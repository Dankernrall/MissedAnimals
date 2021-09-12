package Db.Actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBAddAnimal {
    public void addAnimal(String animalName, int animalType, double animalWeight, boolean isSterilized, String dateOfReceipt) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.h2.Driver");
            conn = ConnectionToDB.getConnection();
            stmt = conn.createStatement();
            stmt.execute("INSERT INTO ALLANIMALS(NAME,TYPE,WEIGHT,STERILIZED,DATE_OF_RECEIPT) VALUES ('" + animalName
                + "'," + animalType + "," + animalWeight + "," + isSterilized + ",'" + dateOfReceipt + "');");
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
