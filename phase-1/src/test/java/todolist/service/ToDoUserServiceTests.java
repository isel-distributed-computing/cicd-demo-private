package todolist.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

// about MockitoExtension
// https://www.infoworld.com/article/3537563/junit-5-tutorial-part-1-unit-testing-with-junit-5-mockito-and-hamcrest.html?page=3
@ExtendWith(MockitoExtension.class)
public class ToDoUserServiceTests {
    public static final String NAME = "name";
    public static final String PWD = "pwd";
    @InjectMocks
    private ToDoUserService userService;

    @Test
    public void testRegisterUser() {
        boolean result = userService.register(NAME, PWD);
        Assertions.assertTrue(result);
    }

    @Test
    public void testExistingRegisteredUser() {
        userService.register(NAME, PWD);
        boolean result = userService.register(NAME, PWD);
        Assertions.assertFalse(result);
    }

    @Test
    public void testLoginUser() throws PasswordMismatchException {
        userService.register(NAME, PWD);
        String token = userService.login(NAME, PWD);
        Algorithm algorithm = Algorithm.HMAC256("secret");
        DecodedJWT jwt = JWT.require(algorithm)
                .build()
                .verify(token);
        String sub = jwt.getClaim("username").asString();
        Assertions.assertEquals(NAME, sub);
    }

    @Test
    public void testValidateToken() {
        userService.register(NAME, PWD);
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withClaim("username", NAME)
                .sign(algorithm);
        Assertions.assertTrue(userService.validateToken(token));
    }

}
