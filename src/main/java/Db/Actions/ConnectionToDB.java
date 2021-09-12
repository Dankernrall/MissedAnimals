package Db.Actions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDB {
    private static Connection conn;

    public static synchronized Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                //conn = DriverManager.getConnection("jdbc:h2:~/TestovoeZadanie/src/main/resources/Database/myDB", "admin", "");
                conn = DriverManager.getConnection("jdbc:h2:file:./myDB", "admin", "");
            } catch (SQLException throwables) {
                System.out.println("Ошибка при получении соединения!");
            }
        }
        return conn;
    }
}
