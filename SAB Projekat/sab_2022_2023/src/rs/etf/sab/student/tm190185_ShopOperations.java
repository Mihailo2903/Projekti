package rs.etf.sab.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.ShopOperations;

/**
 *
 * @author Mico
 */
public class tm190185_ShopOperations implements ShopOperations {

    @Override
    public int createShop(String string, String string1) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select IdCity from City where Name = ?")) {
                ps.setString(1, string1);
                try ( ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return -1;
                    }

                    int idCity = rs.getInt(1);

                    String query1 = "if not exists(select * from Shop where Name = ?)\n"
                            + " insert into Shop(Name, IdCity, Discount) values(?,?,?)";
                    try ( PreparedStatement ps1 = conn.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);) {
                        ps1.setString(1, string);
                        ps1.setString(2, string);
                        ps1.setInt(3, idCity);
                        ps1.setInt(4, 0);

                        ps1.executeUpdate();
                        try ( ResultSet rs1 = ps1.getGeneratedKeys()) {
                            if (rs1.next()) {
                                return rs1.getInt(1);
                            } else {
                                return -1;
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_ShopOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int setCity(int i, String string) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select IdCity from City where Name = ?")) {
                ps.setString(1, string);
                try ( ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return -1;
                    }

                    int idCity = rs.getInt(1);

                    String query1 = "update Shop\n"
                            + " set IdCity = ?\n"
                            + " where IdShop = ?";
                    try ( PreparedStatement ps1 = conn.prepareStatement(query1)) {
                        ps1.setInt(1, idCity);
                        ps1.setInt(2, i);

                        int num = ps1.executeUpdate();
                        if (num == 1) {
                            return 1;
                        }
                        return -1;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_ShopOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int getCity(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select IdCity from Shop where IdShop = ?")) {
                ps.setInt(1, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                    return -1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_ShopOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int setDiscount(int i, int i1) {
        try {
            Connection conn = DB.getInstance().getConnection();
            String query = "update Shop\n"
                    + " set Discount = ?\n"
                    + " where IdShop = ?";
            try ( PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, i1);
                ps.setInt(2, i);

                int num = ps.executeUpdate();
                if (num == 1) {
                    return 1;
                }
                return -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_ShopOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int increaseArticleCount(int i, int i1) {
        try {
            Connection conn = DB.getInstance().getConnection();
            String query = "update Article\n"
                    + " set Count = Count + ?\n"
                    + " where IdArticle = ? ";
            try ( PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, i1);
                ps.setInt(2, i);

                int num = ps.executeUpdate();
                if (num == 1) {
                    return getArticleCount(i);
                }
                return -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_ShopOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int getArticleCount(int i) {
         try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select Count from Article where IdArticle = ?")) {
                ps.setInt(1, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                    return -1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_ShopOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public List<Integer> getArticles(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select IdArticle from Article where IdShop = ?")) {
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
    public int getDiscount(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select Discount from Shop where IdShop = ?")) {
                ps.setInt(1, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                    return -1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_ShopOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

}
