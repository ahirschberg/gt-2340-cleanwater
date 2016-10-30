package fxapp;


import model.PermissionLevel;
import model.Token;
import model.User;

import java.sql.*;
import java.util.Arrays;

class DatabaseManager {
    private ModelPersistenceHelper<User> users = createPersistenceHelper("users");
    private ModelPersistenceHelper<User> sourceReports = createPersistenceHelper("source_reports");
    private ModelPersistenceHelper<User> purityReports = createPersistenceHelper("purity_reports");

    private <M> ModelPersistenceHelper<M> createPersistenceHelper(String tableName) {
        return new ModelPersistenceHelper<>(this, tableName);
    }
    public void init() {
        users.addColumn("username string UNIQUE", User::getUsername);
        users.addColumn("token string UNIQUE", User::getToken);
        users.addColumn("permission integer", (User u) -> u.getPermissionLevel().level);
        users.init();
        sourceReports.addColumn("");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:cleanwater.db");
    }

    public boolean storeUser(User u) {
        System.out.println("Storing " + u);
        try {
            users.store(u);
            return true;
        } catch (SQLException e){
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
