package rs.etf.sab.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.CityOperations;

/**
 *
 * @author Mico
 */
public class tm190185_CityOperations implements CityOperations {

    @Override
    public int createCity(String string) {
        Connection conn = DB.getInstance().getConnection();
        String query = "if not exists(select * from City where Name = ?)\n"
                + " insert into City (Name) values(?)";
        try ( PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, string);
            ps.setString(2, string);

            ps.executeUpdate();
            try ( ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                else return -1;
            }

        } catch (SQLException ex) {
            Logger.getLogger(tm190185_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    @Override
    public List<Integer> getCities() {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select IdCity from City")) {

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
    public int connectCities(int i, int i1, int i2) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select * from Line\n"
                    + " where (IdCity1 = ? and IdCity2 = ?) or (IdCity1 = ? and IdCity2 = ?)")) {
                ps.setInt(1, i);
                ps.setInt(2, i1);
                ps.setInt(3, i1);
                ps.setInt(4, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return -1;
                    }
                    String query1 = "insert into Line (IdCity1, IdCity2, Distance) values (?,?,?)";
                    try ( PreparedStatement ps1 = conn.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);) {
                        ps1.setInt(1, i);
                        ps1.setInt(2, i1);
                        ps1.setInt(3, i2);

                        ps1.executeUpdate();
                        try ( ResultSet rs1 = ps1.getGeneratedKeys()) {
                            if (rs1.next()) {
                                return rs1.getInt(1);
                            }
                            return -1;
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(tm190185_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
                        return -1;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    @Override
    public List<Integer> getConnectedCities(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select IdCity1 from Line where IdCity2 = ? \n"
                    + " union \n"
                    + " select IdCity2 from Line where IdCity1 = ?")) {

                ps.setInt(1, i);
                ps.setInt(2, i);

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
    public List<Integer> getShops(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select IdShop from Shop where IdCity = ?")) {
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

    public List<Integer> getCitiesWithShops() {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select distinct IdCity from Shop")) {
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
}
