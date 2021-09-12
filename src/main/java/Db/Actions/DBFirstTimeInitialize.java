package Db.Actions;

import java.sql.Connection;
import java.sql.Statement;

public class DBFirstTimeInitialize {
    public static void firstInitialize() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("org.h2.Driver");
            conn = ConnectionToDB.getConnection();
            stmt = conn.createStatement();
            stmt.execute("create table ANIMALTYPE(" +
                "ID INT not null primary key," +
                "TYPE_NAME VARCHAR(40) not null)"
            );
            stmt.execute("INSERT INTO ANIMALTYPE VALUES (1,'Кошка/Кот')");
            stmt.execute("INSERT INTO ANIMALTYPE VALUES (2,'Собака')");
            stmt.execute("INSERT INTO ANIMALTYPE VALUES (3,'Хомяк')");
            stmt.execute("INSERT INTO ANIMALTYPE VALUES (4,'Крыса')");
            stmt.execute("create table ALLANIMALS(" +
                "ID              INT auto_increment," +
                "NAME            VARCHAR(50)," +
                "TYPE          INT references ANIMALTYPE(ID)," +
                "WEIGHT          DOUBLE," +
                "STERILIZED      BOOLEAN," +
                "DATE_OF_RECEIPT DATE)"
            );
            stmt.close();
            ;
            conn.close();
        } catch (Exception ex) {
        }
    }
}
