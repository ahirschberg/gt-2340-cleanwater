package controller;

import fxapp.MainFXApplication;
import fxapp.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import model.User;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles the user list screen
 * where admins can (un)ban and delete users.
 */
public class UserListScreenController {
	private MainFXApplication main;
	private UserManager userManager;

	@FXML
	private ListView<User> userList;

	/**
	 * Called automatically on view initialization
	 */
	@FXML
	public void initialize() {

	}

	/**
	 * Registers the main application with this controller
	 *
	 * @param main the main application
	 */
	public void register(MainFXApplication main, UserManager userManager) {
		this.main = main;
		this.userManager = userManager;
	}

	/**
	 * Sets the list view to be a list of all users
	 * using User::toString.
	 */
	public void populateUserList(Stream<User> users) {
		userList.getItems().clear();
		userList.getItems().addAll(users.collect(Collectors.toList()));
	}

	/**
	 * Take the user back to the home page
	 */
	@FXML
	public void onHomeSelected() {
		main.setMainScene();
	}

	/**
	 * Bans the selected users.
	 */
	@FXML
	public void onBan() {
		ObservableList<User> selectedUsers = userList
			.getSelectionModel().getSelectedItems();
		userManager.setBannedStatus(selectedUsers, true);
		populateUserList(userManager.getAllUsers());
	}

	/**
	 * Unbans the selected users.
	 */
	@FXML
	public void onUnban() {
		ObservableList<User> selectedUsers = userList
			.getSelectionModel().getSelectedItems();
		userManager.setBannedStatus(selectedUsers, false);
		populateUserList(userManager.getAllUsers());
    }

	/**
	 * Deletes the selected users.
	 */
	@FXML
	public void onDelete() {
		ObservableList<User> selectedUsers = userList
			.getSelectionModel().getSelectedItems();
		userManager.deleteUsers(selectedUsers);
		populateUserList(userManager.getAllUsers());
	}
}
