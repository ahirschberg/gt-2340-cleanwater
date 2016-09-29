package model;

import java.util.Arrays;

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
}
