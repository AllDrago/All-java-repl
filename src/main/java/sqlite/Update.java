package sqlite;

import java.sql.*;

public class Update {

    private Connection connect() {
        String url = "jdbc:sqlite:/home/user/snap/sqPr.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            // Создание таблицы, если она не существует
            conn.createStatement().execute("CREATE TABLE IF NOT EXISTS Users (\n" +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    " name VARCHAR(20) NOT NULL,\n" +
                    " phone VARCHAR(20) DEFAULT NULL\n" +
                    ");");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void selectAll() {
        String sql = "update Users set name=? where id=?";
        try (Connection conn = this.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
                 stmt.setString(1,"Henry");
                 stmt.setInt(2,2);
                 stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Update app = new Update();
        app.selectAll();
    }
}