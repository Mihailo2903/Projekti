package rs.etf.sab.student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.TransactionOperations;

/**
 *
 * @author Mico
 */
public class tm190185_TransactionOperations implements TransactionOperations {

    @Override
    public BigDecimal getBuyerTransactionsAmmount(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select coalesce(sum(Amount),0)\n"
                    + " from Transaction1\n"
                    + " where IdBuyer = ? and Type = 'B'")) {
                ps.setInt(1, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getBigDecimal(1).setScale(3);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new BigDecimal(-1).setScale(3);
    }

    @Override
    public BigDecimal getShopTransactionsAmmount(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select coalesce(sum(Amount),0)\n"
                    + " from Transaction1\n"
                    + " where Type = 'S' and IdShop = ? ")) {
                ps.setInt(1, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getBigDecimal(1).setScale(3);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new BigDecimal(-1).setScale(3);
    }

    @Override
    public List<Integer> getTransationsForBuyer(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select IdTransaction\n"
                    + " from Transaction1\n"
                    + " where IdBuyer = ? and Type = 'B'")) {
                ps.setInt(1, i);

                List<Integer> list = new ArrayList<>();
                try ( ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(rs.getInt(1));
                    }
                    if(!list.isEmpty())
                        return list;
                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int getTransactionForBuyersOrder(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select IdTransaction\n"
                    + " from Transaction1\n"
                    + " where IdOrder = ? and Type = 'B'")) {
                ps.setInt(1, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int getTransactionForShopAndOrder(int i, int i1) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select IdTransaction\n"
                    + " from Transaction1\n"
                    + " where Type = 'S' and IdOrder = ? and IdShop = ?")) {
                ps.setInt(1, i);
                ps.setInt(2, i1);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public List<Integer> getTransationsForShop(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select IdTransaction\n"
                    + " from Transaction1\n"
                    + " where Type = 'S' and IdShop = ? ")) {
                ps.setInt(1, i);

                List<Integer> list = new ArrayList<>();
                try ( ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(rs.getInt(1));
                    }
                    if(!list.isEmpty())
                        return list;
                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Calendar getTimeOfExecution(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select t.Date\n"
                    + " from Transaction1 t"
                    + " where t.IdTransaction = ?")) {
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
            Logger.getLogger(tm190185_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BigDecimal getAmmountThatBuyerPayedForOrder(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select Amount\n"
                    + " from Transaction1\n"
                    + " where Type = 'B' and IdOrder = ? ")) {
                ps.setInt(1, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getBigDecimal(1).setScale(3);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new BigDecimal(-1).setScale(3);
    }

    @Override
    public BigDecimal getAmmountThatShopRecievedForOrder(int i, int i1) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select coalesce(sum(Amount),0)\n"
                    + " from Transaction1\n"
                    + " where Type = 'S' and IdShop = ?  and IdOrder = ?")) {
                ps.setInt(1, i);
                ps.setInt(2, i1);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getBigDecimal(1).setScale(3);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new BigDecimal(-1).setScale(3);
    }

    @Override
    public BigDecimal getTransactionAmount(int i) {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select Amount\n"
                    + " from Transaction1\n"
                    + " where IdTransaction = ?")) {
                ps.setInt(1, i);

                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getBigDecimal(1).setScale(3);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new BigDecimal(-1).setScale(3);
    }

    @Override
    public BigDecimal getSystemProfit() {
        try {
            Connection conn = DB.getInstance().getConnection();
            try ( PreparedStatement ps = conn.prepareStatement("select coalesce(sum(Amount),0)\n"
                    + " from Transaction1\n"
                    + " where Type = 'Y'")) {
        
                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getBigDecimal(1).setScale(3);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(tm190185_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new BigDecimal(-1).setScale(3);
    }

}
