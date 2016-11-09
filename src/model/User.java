package model;

import fxapp.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
    private static DatabaseManager db;
    private String username;
    private Token token;
    private PermissionLevel permissionLevel;
    private Profile profile;

    public static void registerDatabaseManager(DatabaseManager db) {
        User.db = db;
    }

    public Token getToken() {
        return token;
    }

    public PermissionLevel getPermissionLevel() {
        return this.permissionLevel;
    }

    public String getUsername() {
        return this.username;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public void setProfile(Profile profile) {
        try {
            int id = db.getPersistence(Profile.class).store(profile);
            // fixme hack update row until we have better database tools written
            try (Connection conn = db.getConnection()) {
                PreparedStatement updateProf = conn
                        .prepareStatement("UPDATE users SET profile=(?) "
                               + "WHERE username=(?)");
                updateProf.setInt(1, id);
                updateProf.setString(2, getUsername());
                updateProf.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.profile = profile;
    }

    public User(String username, Token token, PermissionLevel permissionLevel) {
        this(username, token, permissionLevel, new Profile());
    }

    public User(String username, Token token,
                PermissionLevel permissionLevel, Profile profile) {
        this.username = username;
        this.token = token;
        this.permissionLevel = permissionLevel;
        this.profile = profile;
    }
}
