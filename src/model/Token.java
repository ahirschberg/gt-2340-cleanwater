package model;

import fxapp.MainFXApplication;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * This model contains a user's unique identifying information (UID).
 */
public class Token {
    private byte[] hash;

    /**
     * Initialize a new token with the given credentials
     * @param hash the hash of the user's credentials to store
     */
    public Token(byte[] hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Token) {
            Token t = (Token) other;
            return Arrays.equals(this.hash, t.hash);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(hash);
    }

    /**
     * Generate a UID from a user's credentials, and store it in a new token
     * @param user the username credential
     * @param password the password credential
     * @return A new token object with the UID as its hash
     */
    public static Token fromCredentials(String user, String password) {
        byte[] token = null;
        try {
            token = java.security.MessageDigest.getInstance("SHA-1").digest((user + password).getBytes());
        } catch (NoSuchAlgorithmException e) {
            MainFXApplication.LOGGER.log(Level.SEVERE, "No algorithm detected for token.");
            e.printStackTrace();
        }
        return new Token(token);
    }
}
