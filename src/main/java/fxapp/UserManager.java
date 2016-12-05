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
		for (User curUser : allUsers) {
			if (curUser.getToken().equals(token)) {
				return curUser;
			}
		}
		return null;
	}

	/**
	 * Registers a new user.
	 * @param newUser the new user.
	 */
	public boolean registerNewUser(User newUser) {
		try {
            db.getPersistence(User.class).store(newUser);
            allUsers.add(newUser);
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
	public void setBannedStatus(ObservableList<User> selectedUsers, boolean banned) {
		for (User u: selectedUsers) {
			u.setBanned(banned);
		}
	}
	
	/**
	 * Deletes the users with at given indices
	 * @param userIndices the indices of the users to ban.
	 */
	public void deleteUsers(ObservableList<User> selectedUsers) {
        for (User u : selectedUsers) {
			allUsers.remove(u);
            try {
                db.getPersistence(User.class).delete(u);
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
	}
}
