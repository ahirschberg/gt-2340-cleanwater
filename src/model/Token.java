package model;

import fxapp.MainFXApplication;
import javafx.scene.control.PasswordField;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * This model contains a user's unique identifying information (UID).
 */
public class Token {
    private String data;

    /**
     * Initialize a new token with the given credentials
     * @param data the user's credentials to store
     */
    public Token(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Token) {
            Token t = (Token) other;
            return this.data.equals(t.data);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString() {
        return this.data;
    }

    /**
     * Generate a UID from a user's credentials, and store it in a new token
     * @param user the username credential
     * @param password the password credential
     * @throws NoSuchAlgorithmException if the SHA-1 algorithm does not exist
     * @return A new token object with the UID as its hash
     */
    public static Token fromCredentials(String user, String password) throws NoSuchAlgorithmException {
        byte[] token = java.security.MessageDigest.getInstance("SHA-1").digest((user + password).getBytes());
        StringBuilder tokenBuilder = new StringBuilder();
        for (byte b : token) {
            tokenBuilder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return new Token(tokenBuilder.toString());
    }
}
