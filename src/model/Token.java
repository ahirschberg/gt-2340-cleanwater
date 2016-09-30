package model;

import fxapp.MainFXApplication;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;

public class Token {
    private byte[] hash;
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

    // Simulate a secure login system
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
