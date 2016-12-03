package model;

import fxapp.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
    private static DatabaseManager db;
    private final String username;
    private final Token token;
    private final PermissionLevel permissionLevel;
    private Profile profile;
	private boolean banned;

	/**
	 * Returns whether the user is banned.
	 */
	public boolean isBanned() {
		return banned;
	}

	/**
	 * Bans the user
	 */
	public void ban() {
		banned = true;
	}

	/**
	 * Unbans the user.
	 */
	public void unban() {
		banned = false;
	}

	/**
     * stores classwide pointer to report database
     * @param db report database
     */
    public static void registerDatabaseManager(DatabaseManager db) {
        User.db = db;
    }

    /**
     * gets authentication token
     * @return authentication token
     */
    public Token getToken() {
        return token;
    }

    /**
     * gets permission level
     * @return level of permission
     */
    public PermissionLevel getPermissionLevel() {
        return this.permissionLevel;
    }

    /**
     * gets username
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * gets user profile 
     * @return user profile
     */
    public Profile getProfile() {
        return this.profile;
    }

    /**
     * Sets user profile to given
     * @param profile new user profile
     */
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

    /**
     * initializes user
     * @param username username of user
     * @param token authentication token
     * @param permissionLevel permission level of user
     */
    public User(String username, Token token, PermissionLevel permissionLevel) {
        this(username, token, permissionLevel, new Profile());
    }

    /**
     * initializes user
     * @param username username of user
     * @param token authentication token
     * @param permissionLevel permission level of user
     * @param profile profile of user
     */
    public User(String username, Token token,
                PermissionLevel permissionLevel, Profile profile) {
	    this(username, token, permissionLevel, profile, false);
    }
	
    /**
     * initializes user
     * @param username username of user
     * @param token authentication token
     * @param permissionLevel permission level of user
     * @param profile profile of user
     * @param banned whether the user is banned
     */
    public User(String username, Token token,
                PermissionLevel permissionLevel, Profile profile,
                boolean banned) {
        this.username = username;
        this.token = token;
        this.permissionLevel = permissionLevel;
        this.profile = profile;
        this.banned = banned;
    }
}
