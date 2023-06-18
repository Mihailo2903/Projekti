package rs.etf.sab.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.ArticleOperations;

/**
 *
 * @author Mico
 */
public class tm190185_ArticleOperations implements ArticleOperations {

    @Override
    public int createArticle(int i, String string, int i1) {
        Connection conn = DB.getInstance().getConnection();
        String query = "insert into Article (Name,Price,IdShop,Count) " + "values(?,?,?,?)";
        try ( PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, string);
            ps.setInt(2, i1);
            ps.setInt(3, i);
            ps.setInt(4, 0);

            ps.executeUpdate();
            try ( ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(tm190185_ArticleOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        return -1;
    }

}
