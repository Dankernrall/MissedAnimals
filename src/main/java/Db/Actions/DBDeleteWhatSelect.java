package Db.Actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBDeleteWhatSelect {
    public void delete(int id) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.h2.Driver");
            conn = ConnectionToDB.getConnection();
            stmt = conn.createStatement();

            stmt.execute("DELETE FROM ALLANIMALS WHERE ALLANIMALS.ID =" + id);

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
