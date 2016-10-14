package fxapp;

import java.sql.*;

/**
 * Created by alex on 10/14/16.
 */
public class DatabaseManager {
    public boolean init() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException cne){
            cne.printStackTrace();
            return false;
        }
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists users");
        stat.executeUpdate("create table users (name string, token string UNIQUE);");
        PreparedStatement prep = conn.prepareStatement(
            "insert into users values (?, ?);");

        prep.setString(1, "Gandhi");
        prep.setString(2, "abcdefg");
        prep.addBatch();
        prep.setString(1, "Alex");
        prep.setString(2, "defghi");
        prep.addBatch();

        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);

        ResultSet rs = stat.executeQuery("select * from users;");
        while (rs.next()) {
            System.out.printf("got: id: %s name: %s token: %s\n", rs.getRow(), rs.getString("name"), rs.getString("token"));
        }
        rs.close();
        conn.close();

        return true;
    }
}
