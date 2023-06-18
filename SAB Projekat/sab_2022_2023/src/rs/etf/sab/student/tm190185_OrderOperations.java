package rs.etf.sab.student;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.OrderOperations;
import rs.etf.sab.operations.ShopOperations;

/**
 *
 * @author Mico
 */
public class tm190185_OrderOperations implements OrderOperations {

    @Override
    public int addArticle(int i, int i1, int i2) {
        if (!getState(i).equals("created")) {
            return -1;
        }

        ShopOperations sop = new tm190185_ShopOperations();
        int numberInShop = sop.getArticleCount(i1);
        if (numberInShop < i2) {
            return -1;
        }
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select IdItem from Item where IdOrder = ? and IdArticle = ?")) {
                ps.setInt(1, i);
                ps.setInt(2, i1);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int idIt = rs.getInt(1);
                        String query1 = "update Item set Count = Count + ? where IdItem = ?";
                        try ( PreparedStatement ps1 = conn.prepareStatement(query1)) {
                            ps1.setInt(1, i2);
                            ps1.setInt(2, idIt);

                            ps1.executeUpdate();
                            sop.increaseArticleCount(i1, i2 * (-1));
                            return idIt;
                        }
                    } else {
                        String query2 = "insert into Item(IdArticle,IdOrder,Count,Cost) values(?,?,?,0)";
                        try ( PreparedStatement ps2 = conn.prepareStatement(query2, PreparedStatement.RETURN_GENERATED_KEYS);) {
                            ps2.setInt(1, i1);
                            ps2.setInt(2, i);
                            ps2.setInt(3, i2);

                            ps2.executeUpdate();
                            try ( ResultSet rs2 = ps2.getGeneratedKeys()) {
                                if (rs2.next()) {
                                    sop.increaseArticleCount(i1, i2 * (-1));
                                    return rs2.getInt(1);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int removeArticle(int i, int i1) {
        if (!getState(i).equals("created")) {
            return -1;
        }
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select IdItem from Item where IdOrder = ? and IdArticle = ? and Count > 0")) {
                ps.setInt(1, i);
                ps.setInt(1, i1);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int idIt = rs.getInt(1);
                        String query1 = "update Item set Count = Count - 1 where IdItem = ?;\n"
                                + " update Article set Count = Count + 1 where IdArticle = ?;";
                        try ( PreparedStatement ps1 = conn.prepareStatement(query1)) {
                            ps1.setInt(1, idIt);
                            ps1.setInt(2, i1);

                            ps1.executeUpdate();
                            return 1;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public List<Integer> getItems(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select IdItem from Item where IdOrder = ?")) {
                ps.setInt(1, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    List<Integer> list = new ArrayList<>();
                    while (rs.next()) {
                        list.add(rs.getInt(1));
                    }
                    return list;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int completeOrder(int i) {
        if (!getState(i).equals("created")) {
            return -1;
        }
        Calendar calendar = new tm190185_GeneralOperations().getCurrentTime();
        Date currTime = calendar.getTime();
        Connection conn = DB.getInstance().getConnection();

        String query = " { call SP_FINAL_PRICE(?,?,?,?) }";
        try ( CallableStatement cs = conn.prepareCall(query);) {
            cs.setInt(1, i);
            cs.setDate(2, new java.sql.Date(currTime.getTime()));
            cs.registerOutParameter(3, java.sql.Types.INTEGER);
            cs.registerOutParameter(4, java.sql.Types.INTEGER);

            cs.execute(); //popunjen order sa finalPrice, fullPrice, poslata, promenjen Status, skinut kredit kupcu, napravljena Transakcija
            int allowed = cs.getInt(3);
            if (allowed == 0) {
                return -1;
            }

            int BuyerCityId = cs.getInt(4);

            tm190185_CityOperations cop = new tm190185_CityOperations();
            List allCities = cop.getCities();
            int numOfCities = allCities.size();

            int d[], p[], a[][];
            d = new int[numOfCities];
            p = new int[numOfCities];
            a = new int[numOfCities][numOfCities];
            for (int u = 0; u < numOfCities; u++) {
                for (int j = 0; j < numOfCities; j++) {
                    if (u == j) {
                        a[u][j] = 0;
                    } else {
                        a[u][j] = -1;
                    }
                }
            }
            try ( PreparedStatement ps1 = conn.prepareStatement("select IdCity1, IdCity2, Distance from Line")) {
                try ( ResultSet rs1 = ps1.executeQuery()) {
                    while (rs1.next()) {
                        int c1 = rs1.getInt(1);
                        int c2 = rs1.getInt(2);
                        int dist = rs1.getInt(3);
                        a[c1 - 1][c2 - 1] = dist;
                        a[c2 - 1][c1 - 1] = dist;
                    }
                }
            }

            Dijkstra(numOfCities, a, BuyerCityId - 1, d, p); // u d i p najkraci putevi od grada kupca do ostalih gradova

            List<Integer> citiesAssociatedWithOrder = new ArrayList<>();
            try ( PreparedStatement ps2 = conn.prepareStatement("select distinct s.IdCity\n"
                    + " from Order1 o, Item i, Article a, Shop s\n"
                    + " where o.IdOrder = ? and o.IdOrder = i.IdOrder and  i.IdArticle = a.IdArticle and a.IdShop = s.IdShop")) {
                ps2.setInt(1, i);

                try ( ResultSet rs2 = ps2.executeQuery()) {
                    while (rs2.next()) {
                        citiesAssociatedWithOrder.add(rs2.getInt(1));
                    }
                }
            }

            List<Integer> citiesWithShops = new tm190185_CityOperations().getCitiesWithShops();
            int assemblyCity = -1, closestToBuyer = -1;
            int minDistAss = Integer.MAX_VALUE, minDistOrd = Integer.MAX_VALUE;

            for (int u = 0; u < numOfCities; u++) {
                if (d[u] != -1 && d[u] < minDistAss && citiesWithShops.contains(u + 1)) {
                    minDistAss = d[u];
                    assemblyCity = u;
                }
                if (d[u] != -1 && d[u] < minDistOrd && citiesAssociatedWithOrder.contains(u + 1)) {
                    minDistOrd = d[u];
                    closestToBuyer = u + 1;
                }
            }
            
           /* for (int u = 0; u < numOfCities; u++) {
                for (int j = 0; j < numOfCities; j++) {
                    System.out.print(a[u][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
            /*for(int asss: d) System.out.print(asss + " ");
            System.out.println();
            for(int asss: p) System.out.print(asss + " ");
            System.out.println();
            System.out.println(assemblyCity);*/

            d = new int[numOfCities];
            p = new int[numOfCities];

            Dijkstra(numOfCities, a, assemblyCity, d, p); // u d i p najkraci putevi grada sklapanja do ostalih

            int daysToAssemble = 0; //dani do sklapanja

            for (int u = 0; u < numOfCities; u++) {
                if (d[u] != -1 && d[u] > daysToAssemble && citiesAssociatedWithOrder.contains(u + 1)) {
                    daysToAssemble = d[u];
                }
            }

            Date dateOfSending, dateOfAssembly, dateOfArriving;
            dateOfSending = currTime;
            calendar.add(Calendar.DAY_OF_MONTH, daysToAssemble);
            dateOfAssembly = calendar.getTime();

            int u = BuyerCityId - 1;
            Stack<Integer> path = new Stack<>();
            do {
                path.push(u); //grad kupca - .. - gradsklapanja
                u = p[u];
            } while (u != -1);

            int prev = -1, curr = -1;
            while (!path.empty()) {
                prev = curr;
                curr = path.pop();
                if (prev == -1) {
                    String query3 = "insert into Location(IdCity, IdOrder, Date) values (?,?,?)";
                    try ( PreparedStatement ps3 = conn.prepareStatement(query3);) {
                        ps3.setInt(1, curr + 1);
                        ps3.setInt(2, i);
                        ps3.setDate(3, new java.sql.Date(dateOfAssembly.getTime()));

                        ps3.executeUpdate();
                    }
                } else {
                    int dateDiff = a[curr][prev];
                    calendar.add(Calendar.DAY_OF_MONTH, dateDiff);
                    String query4 = "insert into Location(IdCity, IdOrder, Date) values (?,?,?)";
                    try ( PreparedStatement ps4 = conn.prepareStatement(query4);) {
                        ps4.setInt(1, curr + 1);
                        ps4.setInt(2, i);
                        ps4.setDate(3, new java.sql.Date(calendar.getTime().getTime()));

                        ps4.executeUpdate();
                    }
                }
            }
            dateOfArriving = calendar.getTime();

            String query5 = "update Order1\n"
                    + " set DateOfArriving = ?, DateOfAssembly = ?, DateOfSending = ?, IdCityClosest = ?\n"
                    + " where IdOrder = ?";
            try ( PreparedStatement ps5 = conn.prepareStatement(query5);) {
                ps5.setDate(1, new java.sql.Date(dateOfArriving.getTime()));
                ps5.setDate(2, new java.sql.Date(dateOfAssembly.getTime()));
                ps5.setDate(3, new java.sql.Date(dateOfSending.getTime()));
                ps5.setInt(4, closestToBuyer);
                ps5.setInt(5, i);

                ps5.executeUpdate();
            }

            calendar.setTime(currTime);
            tm190185_GeneralOperations gop = new tm190185_GeneralOperations();
            gop.setInitialTime(calendar);
            gop.time(0);

        } catch (SQLException ex) {
            Logger.getLogger(tm190185_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        return 1;
    }

    @Override
    public BigDecimal getFinalPrice(int i) {
        if (getState(i).equals("created")) {
            return new BigDecimal(-1);
        }
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select FinalPrice from Order1 where IdOrder = ?")) {
                ps.setInt(1, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getBigDecimal(1).setScale(3);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new BigDecimal(-1);
    }

    @Override
    public BigDecimal getDiscountSum(int i) {
        if (getState(i).equals("created")) {
            return new BigDecimal(-1);
        }
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select FullPrice - FinalPrice from Order1 where IdOrder = ?")) {
                ps.setInt(1, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getBigDecimal(1).setScale(3);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new BigDecimal(-1);
    }

    @Override
    public String getState(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select Status from Order1 where IdOrder = ?")) {
                ps.setInt(1, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString(1);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    @Override
    public Calendar getSentTime(int i) {
        if (getState(i).equals("created")) {
            return null;
        }
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select DateOfSending from Order1 where IdOrder = ?")) {
                ps.setInt(1, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Calendar c = new GregorianCalendar();
                        c.setTime(rs.getDate(1));
                        return c;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Calendar getRecievedTime(int i) {
        if (!getState(i).equals("arrived")) {
            return null;
        }
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select DateOfArriving from Order1 where IdOrder = ?")) {
                ps.setInt(1, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Calendar c = new GregorianCalendar();
                        c.setTime(rs.getDate(1));
                        return c;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int getBuyer(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select IdBuyer from Order1 where IdOrder = ?")) {
                ps.setInt(1, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int getLocation(int i) {
        if (getState(i).equals("created")) {
            return -1;
        }
        Calendar c = new tm190185_GeneralOperations().getCurrentTime();
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select top 1 IdCity\n"
                    + " from Location\n"
                    + " where IdOrder = ? and Date <= ?\n"
                    + " order by Date desc")) {
                ps.setInt(1, i);
                ps.setDate(2, new java.sql.Date(c.getTime().getTime()));

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                       return rs.getInt(1);
                    }
                    return getClosestCity(i);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    private int getClosestCity(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select IdCityClosest from Order1 where IdOrder = ?")) {
                ps.setInt(1, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    private int minIndeks(int d[], boolean m[], int n) {
        int k = -1;
        for (int u = 0; u < n; u++) {
            if (!m[u] && d[u] >= 0) {
                if (k == -1 || d[u] < d[k]) {
                    k = u;
                }
            }
        }
        return k;
    }

    private void Dijkstra(int n, int a[][], int s, int d[], int p[]) {
        boolean m[] = new boolean[n];

        for (int u = 0; u < n; u++) {
            d[u] = -1;
            p[u] = -1;
            m[u] = false;
        }

        d[s] = 0;
        for (int i = 0; i < n; i++) {
            int u = minIndeks(d, m, n);
            if (u == -1) {
                break;
            }
            m[u] = true;
            for (int v = 0; v < n; v++) {
                if (!m[v] && a[u][v] > 0) {
                    if (d[v] < 0 || d[v] > d[u] + a[u][v]) {
                        d[v] = d[u] + a[u][v];
                        p[v] = u;
                    }
                }
            }
        }
        
       /* for(int asss: d) System.out.print(asss + " ");
            System.out.println();
            for(int asss: p) System.out.print(asss + " "); System.out.println();  System.out.println();*/
    }

}
