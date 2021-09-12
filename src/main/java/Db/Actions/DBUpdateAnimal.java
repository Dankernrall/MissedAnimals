package Db.Actions;

import java.sql.SQLException;
import java.sql.Statement;

public class DBUpdateAnimal {
    public void addAnimal(int id, String animalName, int animalType, double animalWeight, boolean isSterilized, String dateOfReceipt) {
        java.sql.Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.h2.Driver");
            conn = ConnectionToDB.getConnection();
            stmt = conn.createStatement();

            stmt.execute("UPDATE ALLANIMALS SET NAME ='" + animalName + "',TYPE = " + animalType + ", WEIGHT = " + animalWeight
                + ",STERILIZED = " + isSterilized + ",DATE_OF_RECEIPT = '" + dateOfReceipt + "' WHERE ID = " + id + ";");

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
