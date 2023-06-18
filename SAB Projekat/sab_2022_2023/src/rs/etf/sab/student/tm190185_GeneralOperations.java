package rs.etf.sab.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.GeneralOperations;

/**
 *
 * @author Mico
 */
public class tm190185_GeneralOperations implements GeneralOperations {

    //private static Calendar calendar = new GregorianCalendar();
    private static Calendar currcalendar = new GregorianCalendar();

    @Override
    public void setInitialTime(Calendar clndr) {
        //calendar = clndr;
        Date d = clndr.getTime();
        currcalendar = new GregorianCalendar();
        currcalendar.setTime(d);
    }

    @Override
    public Calendar time(int i) {
        currcalendar.add(Calendar.DATE, i);

        Connection conn = DB.getInstance().getConnection();
        String query = "update Order1 set Status = 'arrived' where Status = 'sent' and DateOfArriving <= ?";
        try ( PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setDate(1, new java.sql.Date(currcalendar.getTime().getTime()));

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(tm190185_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currcalendar;
    }

    @Override
    public Calendar getCurrentTime() {
        return currcalendar;
    }

    @Override
    public void eraseAll() {
        Connection conn = DB.getInstance().getConnection();
        String query = "delete from Location;\n"
                + " delete from Line;\n"
                + " delete from Transaction1;\n"
                + " delete from Item;\n"
                + " delete from Article;\n"
                + " delete from Shop;\n"
                + " delete from Order1;\n"
                + " delete from Buyer;\n"
                + " delete from City;\n"
                + "\n"
                + " DBCC CHECKIDENT (Location, RESEED, 0);\n"
                + " DBCC CHECKIDENT (Line, RESEED, 0);\n"
                + " DBCC CHECKIDENT (Transaction1, RESEED, 0);\n"
                + " DBCC CHECKIDENT (Item, RESEED, 0);\n"
                + " DBCC CHECKIDENT (Article, RESEED, 0);\n"
                + " DBCC CHECKIDENT (Shop, RESEED, 0);\n"
                + " DBCC CHECKIDENT (Order1, RESEED, 0);\n"
                + " DBCC CHECKIDENT (Buyer, RESEED, 0);\n"
                + " DBCC CHECKIDENT (City, RESEED, 0);";
        try ( PreparedStatement ps = conn.prepareStatement(query);) {

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(tm190185_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
