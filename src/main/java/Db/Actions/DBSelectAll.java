package Db.Actions;

import Model.Animal;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBSelectAll {

    public ObservableList connect(ObservableList Data) {
        Connection conn = null;
        Statement stmt = null;
        Data.clear();
        try {
            Class.forName("org.h2.Driver");
            conn = ConnectionToDB.getConnection();
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(
                "SELECT ALLANIMALS.ID,ALLANIMALS.NAME, ANIMALTYPE.TYPE_NAME, ALLANIMALS.WEIGHT,ALLANIMALS.STERILIZED,ALLANIMALS.DATE_OF_RECEIPT\n" +
                    "FROM ALLANIMALS,ANIMALTYPE\n" +
                    "WHERE ALLANIMALS.TYPE = ANIMALTYPE.ID");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("Name");
                String type = rs.getString("Type_Name");
                double weight = rs.getDouble("Weight");
                boolean sterilized = rs.getBoolean("Sterilized");
                Date date = rs.getDate("Date_OF_RECEIPT");
                Data.add(new Animal(id, name, type, weight, sterilized ? "Да" : "Нет", date.toLocalDate()));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            DBFirstTimeInitialize.firstInitialize();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                return Data;
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
        return Data;
    }
}
