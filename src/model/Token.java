package model;

import java.security.NoSuchAlgorithmException;

/**
 * This model contains a user's unique identifying information (UID).
 */
public class Token {
    private String data;

    /**
     * Initialize a new token with the given credentials
     *
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

    public String getData() {
        return this.data;
    }

    /**
     * Generate a UID from a user's credentials, and store it in a new token
     *
     * @param user     the username credential
     * @param password the password credential
     * @return A new token object with the UID as its hash
     * @throws NoSuchAlgorithmException if the SHA-1 algorithm does not exist
     */
}
