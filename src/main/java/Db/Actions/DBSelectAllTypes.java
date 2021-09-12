package Db.Actions;

import Model.AnimalTypes;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBSelectAllTypes {
    public ObservableList getAllTypes(ObservableList typesData) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.h2.Driver");
            conn = ConnectionToDB.getConnection();
            stmt = conn.createStatement();
            typesData.clear();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ANIMALTYPE");
            while (rs.next()) {
                int id = rs.getInt("id");
                String type_name = rs.getString("Type_Name");
                typesData.add(new AnimalTypes(id, type_name));
            }
            rs.close();
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
        return typesData;
    }
}
