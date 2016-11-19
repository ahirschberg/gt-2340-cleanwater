package fxapp;

import model.Token;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class AuthenticationManager {
    private final MessageDigest messageDigest;

    /**
     * authentication manager
     * @param algorithm to pass in
     * @throws NoSuchAlgorithmException does not exist
     */
    public AuthenticationManager(String algorithm)
            throws NoSuchAlgorithmException {
        this(MessageDigest.getInstance(algorithm));
    }

    /**
     * manager
     * @param digest to pass in
     */
    private AuthenticationManager(MessageDigest digest) {
        this.messageDigest = digest;
    }

    /**
     * token gen
     * @param user to generate from
     * @param password user password
     * @return Token for user
     */
    public Token tokenFromCredentials(String user, String password) {
        byte[] token = messageDigest.digest((user + password).getBytes());
        StringBuilder tokenBuilder = new StringBuilder();
        for (byte b : token) {
            tokenBuilder.append(Integer.toString((b & 0xff)
                    + 0x100, 16).substring(1));
        }
        return new Token(tokenBuilder.toString());
    }
}
