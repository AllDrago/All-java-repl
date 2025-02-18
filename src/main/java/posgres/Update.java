package posgres;

import java.sql.*;

public class Update {

    private Connection connect() {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/somedb", "someuser", "somepass");
            conn.createStatement().execute("CREATE TABLE IF NOT EXISTS \"Users\" (\n" +
                    " id INT GENERATED ALWAYS AS IDENTITY, \n" +
                    " name VARCHAR(20) NOT NULL,\n" +
                    " PRIMARY KEY (id)\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"Contacts\" (\n" +
                    " id INT GENERATED AS IDENTITY, \n" +
                    " \"customerId\" INT, \n" +
                    " \"contactName\" VARCHAR(255) NOT NULL, \n" +
                    " phone VARCHAR(15), \n" +
                    " email VARCHAR(100), \n" +
                    " PRIMARY KEY (id), \n" +
                    " CONSTRAINT \"fk_Users_Contacts\"\n" +
                    " FOREIGN KEY (\"customerId\") \n" +
                    " REFERENCES \"Users\"(id)\n" +
                    ");");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void selectAll() {
        String sql = "SELECT * FROM \"Users\" u LEFT JOIN \"Contacts\" c ON c.\"customerId\" = u.id;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Update app = new Update();
        app.selectAll();
    }
}
