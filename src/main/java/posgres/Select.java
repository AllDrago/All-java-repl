package posgres;

import models.Contact;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Select {

    private Connection connect() {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/somedb", "someuser", "somepass");
            conn.createStatement().execute("CREATE TABLE IF NOT EXISTS \"Users\" (\n" +
                    " id INT GENERATED ALWAYS AS IDENTITY, \n" +
                    " name VARCHAR(20) not null,\n" +
                    " PRIMARY KEY (id)\n" +
                    ");\n" +
                    "create  table IF NOT EXISTS \"Contacts\"(\n" +
                    " id INT generated  as identity, \n" +
                    "\"customerId\" INT, \n" +
                    " \"contactName\" VARCHAR(255) not null, \n" +
                    " phone VARCHAR(15), \n" +
                    "email VARCHAR(100), \n" +
                    "primary key(id), \n" +
                    "constraint \"fk_Users_Contacts\"\n" +
                    "foreign key(\"customerId\") \n" +
                    "\t references \"Users\"(id)\n" +
                    ");");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void selectAll() {
        //String sql = "SELECT * FROM \"Users\" u LEFT JOIN \"Contacts\" c ON c.\"customerId\" = u.id;";
        String sql = "SELECT u.id as \"Users->Id\", u.\"name\"  as \"Users->name\", c.id as  contactId, c.\"contactName\"," +
                " c.phone, c.email  FROM \"Users\" u LEFT JOIN \"Contacts\" c ON c.\"customerId\" = u.id;";
        // String sql = "select id, name from \"Users\" where name like '%Petya%'";

        List<User> users = new ArrayList<>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            User tempUser = null;
            List<Contact> tmpContacts = new ArrayList<>();
            while (rs.next()) {
                if (tempUser != null && tempUser.id != rs.getInt("Users->Id")) {
                    tempUser.contacts = tmpContacts;
                    users.add(tempUser);
                    tempUser = null;
                }
                tempUser = new User(rs.getInt("Users->Id"),
                        rs.getString("Users->name"),
                        tmpContacts);
                tmpContacts.add(new Contact(rs.getInt("contactId"),
                        rs.getInt("Users->Id"),
                        rs.getString("contactName"),
                        rs.getString("phone"),
                        rs.getString("email")));
            }
            if (tempUser != null) {
                tempUser.contacts = tmpContacts;
                users.add(tempUser);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(users);
    }

    public static void main(String[] args) {
        Select app = new Select();
        app.selectAll();
    }
}