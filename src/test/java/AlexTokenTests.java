import fxapp.AuthenticationManager;
import model.Token;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import static org.hamcrest.CoreMatchers.*;

import java.security.NoSuchAlgorithmException;

public class AlexTokenTests {
    private AuthenticationManager authMgr;

    @Before
    public void setup() throws NoSuchAlgorithmException {
        authMgr = new AuthenticationManager("MD5");
    }

    @Test
    public void test_tokenEquals() {
        Token first = new Token("abcdefg");
        Token second = new Token("abcdefg");
        Assert.assertThat(first, equalTo(second));
    }

    @Test
    public void test_tokenNotEquals() {
        Token first = new Token("abcdefg");
        Token second = new Token("123456");
        Assert.assertNotEquals(first, second);
    }

    @Test(expected = NoSuchAlgorithmException.class)
    public void test_authenticationManagerThrows() throws Exception {
        new AuthenticationManager("this_algorithm_does_not_exist");
    }

    @Test
    public void test_resultReproducable() {
        Token firstResult = authMgr.tokenFromCredentials("a", "b");
        Token secondResult = authMgr.tokenFromCredentials("a", "b");
        Assert.assertEquals(firstResult, secondResult);
    }

    @Test
    public void test_resultNotEquals() {
        Token firstResult = authMgr.tokenFromCredentials("a", "b");
        Token secondResult = authMgr.tokenFromCredentials("c", "d");
        Assert.assertNotEquals(firstResult, secondResult);
    }

    @Test
    public void test_tokenIsHashed() {
        Token t = authMgr.tokenFromCredentials("this_string", "should_not_appear");
        Assert.assertThat(t.getData(), not(containsString("should")));
    }

}
