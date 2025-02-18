package posgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {


    public static void connect() {
        Connection conn = null;
        try {
            //db parameters

            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/somedb", "someuser", "somepass");

            System.out.println("Connection to POSTGRES has been established");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        connect();
    }


}



