package fxapp;

import model.Token;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by alex on 11/9/16.
 */
public class AuthenticationManager {
    private MessageDigest messageDigest;
    public AuthenticationManager(String algorithm) throws NoSuchAlgorithmException {
        this(MessageDigest.getInstance(algorithm));
    }

    public AuthenticationManager(MessageDigest digest) {
        this.messageDigest = digest;
    }

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
