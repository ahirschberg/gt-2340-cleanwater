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
	 * Sets the user's ban status
	 */
	public void setBanned(boolean banned) {
		this.banned = banned;
        try {
            db.getPersistence(User.class).update(this, "banned", banned);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

	/**
	 * Gets a human readable string
	 * of the user for the admin user list.
	 */
	public String toString() {
		return username + " "
			+ permissionLevel.toString()
			+ (banned ? " [BANNED]" : "");
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
            db.getPersistence(User.class).update(this, "profile", id);
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
