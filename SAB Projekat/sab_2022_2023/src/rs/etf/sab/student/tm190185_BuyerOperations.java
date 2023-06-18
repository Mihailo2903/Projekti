package rs.etf.sab.student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.BuyerOperations;

/**
 *
 * @author Mico
 */
public class tm190185_BuyerOperations implements BuyerOperations {

    @Override
    public int createBuyer(String string, int i) {
        Connection conn = DB.getInstance().getConnection();
        String query = "insert into Buyer (Name,Credit,IdCity) " + "values(?,?,?)";
        try ( PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, string);
            ps.setInt(2, 0);
            ps.setInt(3, i);

            ps.executeUpdate();
            try ( ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(tm190185_BuyerOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        return -1;
    }

    @Override
    public int setCity(int i, int i1) {
        Connection conn = DB.getInstance().getConnection();
        String query = "if exists(select * from City where IdCity = ?)\n"
                + " update Buyer set IdCity = ? where IdBuyer = ?";
        try ( PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, i1);
            ps.setInt(2, i1);
            ps.setInt(3, i);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(tm190185_BuyerOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        return 1;
    }

    @Override
    public int getCity(int i) {
        Connection conn = DB.getInstance().getConnection();
        String query = "select IdCity from Buyer where IdBuyer = ?";
        try ( PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, i);
            
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(tm190185_BuyerOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        return -1;
    }

    @Override
    public BigDecimal increaseCredit(int i, BigDecimal bd) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try (PreparedStatement ps = conn.prepareStatement("select * from Buyer where IdBuyer = ?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
                ps.setInt(1, i);
                
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        double credit = rs.getDouble("Credit");
                        double increase = bd.doubleValue(); 
                        rs.updateDouble("Credit", credit + increase);
                        rs.updateRow();
                        
                        return new BigDecimal(credit+increase).setScale(3);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_BuyerOperations.class.getName()).log(Level.SEVERE, null, ex);
            return new BigDecimal(-1);
        }
        return new BigDecimal(-1);
    }

    @Override
    public int createOrder(int i) {
        Connection conn = DB.getInstance().getConnection();
        String query = "insert into Order1 (IdBuyer,Status) " + "values(?,?)";
        try ( PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);) {
            ps.setInt(1, i);
            ps.setString(2, "created");
      
            ps.executeUpdate();
            try ( ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(tm190185_BuyerOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        return -1; 
    }

    @Override
    public List<Integer> getOrders(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try (PreparedStatement ps = conn.prepareStatement("select IdOrder from Order1 where IdBuyer = ?")) {
                ps.setInt(1, i);
                
                try (ResultSet rs = ps.executeQuery()) {
                    List<Integer> list = new ArrayList<>();
                    while (rs.next()) {
                        list.add(rs.getInt(1));
                    }
                    return list;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_BuyerOperations.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public BigDecimal getCredit(int i) {
         try {
            Connection conn = DB.getInstance().getConnection();
            try (PreparedStatement ps = conn.prepareStatement("select Credit from Buyer where IdBuyer = ?")) {
                ps.setInt(1, i);
                
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new BigDecimal(rs.getDouble(1)).setScale(3);
                    }
                    return new BigDecimal(-1).setScale(3);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_BuyerOperations.class.getName()).log(Level.SEVERE, null, ex);
            return new BigDecimal(-1);
        }
    }

}
