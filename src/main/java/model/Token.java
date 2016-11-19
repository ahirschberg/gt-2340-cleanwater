package model;


/**
 * This model contains a user's unique identifying information (UID).
 */
public class Token {
    private final String data;

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

    /**
     * gets data local variable
     * @return data token
     */
    public String getData() {
        return this.data;
    }

}
