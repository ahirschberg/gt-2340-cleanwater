package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.User;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles the user list screen
 * where admins can (un)ban and delete users.
 */
public class UserListScreenController {
    private MainFXApplication main;

    @FXML
    private ListView<String> userList;

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
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

	public void populateUserList() {
		
	}

	/**
     * Take the user back to the home page
     */
    @FXML
    public void onHomeSelected() {
        main.setMainScene();
    }
}
