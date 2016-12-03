package fxapp;

import model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import model.Token;
import javafx.collections.ObservableList;

/**
 * Manages all users (not just the active one).
 */
public class UserManager {
    private List<User> allUsers;
    private final DatabaseManager db;

    /**
     * Initializes user manager
     * @param db database manager to initialize user list with.
     */
    public UserManager(DatabaseManager db) {
        this.db = db;
        try {
            allUsers = db.getPersistence(User.class).retrieveAll();
        } catch (SQLException e) {
            e.printStackTrace();
            allUsers = new LinkedList<>();
        }
    }

    /**
     * Returns all users.
     *
     * @return stream of all users.
     */
    public Stream<User> getAllUsers() {
	    return allUsers.stream();
    }

	/**
	 * Logs in a user
	 * if they use the correct username and password
	 * in the form of the provided token.
	 * @param token authentication token.
	 */
	public User logInUser(Token token) {
		User loggedInUser = null;
		try {
			loggedInUser = db.getPersistence(User.class)
				.retrieveOne("token", token.toString());
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		if (loggedInUser != null) {
			if (loggedInUser.isBanned()) {
				return null;
			}
		}
		return loggedInUser;
	}

	/**
	 * Registers a new user.
	 * @param newUser the new user.
	 */
	public boolean registerNewUser(User newUser) {
		try {
			allUsers.add(newUser);
            db.getPersistence(User.class).store(newUser);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}

	/**
	 * Bans the users with at given indices
	 * @param userIndices the indices of the users to ban.
	 */
	public void banUsers(ObservableList<Integer> userIndices) {
		for (Integer userIndex : userIndices) {
			allUsers.get(userIndex).ban();
		}
	}
	
	/**
	 * Unbans the users with at given indices
	 * @param userIndices the indices of the users to ban.
	 */
	public void unbanUsers(ObservableList<Integer> userIndices) {
		for (Integer userIndex : userIndices) {
			allUsers.get(userIndex).unban();
		}
	}
	
	/**
	 * Deletes the users with at given indices
	 * @param userIndices the indices of the users to ban.
	 */
	public void deleteUsers(ObservableList<Integer> userIndices) {
		for (Integer userIndex : userIndices) {
			allUsers.remove(userIndex);
		}
	}
}
