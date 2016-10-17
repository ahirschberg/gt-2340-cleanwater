package model;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by jster on 9/20/2016.
 */
public class User {
    private String username;
    private Token token;
    private PermissionLevel permissionLevel;
    private Profile profile;

    public Token getToken() { return token; }

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
        this.profile = profile;
    }

    public User(String username, Token token, PermissionLevel permissionLevel) {
        this.username = username;
        this.token = token;
        this.permissionLevel = permissionLevel;
    }
}
