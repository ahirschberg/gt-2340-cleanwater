package fxapp;


import model.PermissionLevel;
import model.Token;
import model.User;

import java.sql.*;
import java.util.Arrays;

public class DatabaseManager {
    public void init() {
        try(Connection conn = getConnection()) {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='users';");
            if (!rs.next()) {
                conn.createStatement().executeUpdate("create table users (username string, token string UNIQUE, permission integer);");
            }
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:cleanwater.db");
    }

    public boolean storeUser(User u) {
        try (Connection conn = getConnection()) {
            try (PreparedStatement checkExists = conn.prepareStatement("SELECT * FROM users where username=(?)")) {
                checkExists.setString(1, u.getUsername());
                try (ResultSet rs = checkExists.executeQuery()) {
                    if (rs.next()) {
                        return false;
                    }
                }
            }
            try (PreparedStatement prep = conn.prepareStatement("insert into users values (?, ?, ?);")) {
                prep.setString(1, u.getUsername());
                prep.setString(2, u.getToken().toString());
                prep.setInt(3, u.getPermissionLevel().level);
                prep.addBatch();
                prep.executeBatch();
                prep.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUser(Token t) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement prep = conn.prepareStatement("select * from users WHERE token=(?)");
            prep.setString(1, t.toString());
            ResultSet match = prep.executeQuery();
            if (match.next()) {
                final int permissionLevelInt = match.getInt("permission");
                PermissionLevel permissionLevel = Arrays // find first PermissionLevel with matching integer
                        .stream(PermissionLevel.values())
                        .filter((pl) -> pl.level == permissionLevelInt)
                        .findFirst()
                        .get();
                return new User(match.getString("username"), new Token(match.getString("token")), permissionLevel);
            }
        }
        return null;
    }

    public DatabaseManager() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        init();
    }
}
