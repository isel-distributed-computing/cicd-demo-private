package todolist.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import todolist.model.User;
import todolist.repository.UserRepository;

import static org.mockito.Mockito.when;

// about MockitoExtension
// https://www.infoworld.com/article/3537563/junit-5-tutorial-part-1-unit-testing-with-junit-5-mockito-and-hamcrest.html?page=3
@ExtendWith(MockitoExtension.class)
class ToDoUserServiceTests {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ToDoUserService toDoUserService;

    public static final String NAME = "name";
    public static final String PWD = "pwd";
    private final String SECRET = Dotenv.load().get("JWT_SECRET");

    @Before
    public void setUp() {
        when(userRepository.save(new User(NAME, PWD, "dummy salt")))
                .thenReturn(new User(NAME, PWD, "dummy salt"));
    }

    @Test
    public void testRegisterUser() {
        boolean result = toDoUserService.register(NAME, PWD);
        Assertions.assertTrue(result);
    }

    @Test
    public void testExistingRegisteredUser() {
        toDoUserService.register(NAME, PWD);
        boolean result = toDoUserService.register(NAME, PWD);
        Assertions.assertFalse(result);
    }

    @Test
    public void testLoginUser() throws PasswordMismatchException {
        toDoUserService.register(NAME, PWD);
        String token = toDoUserService.login(NAME, PWD);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        DecodedJWT jwt = JWT.require(algorithm)
                .build()
                .verify(token);
        String sub = jwt.getClaim("username").asString();
        Assertions.assertEquals(NAME, sub);
    }

    @Test
    public void testValidateToken() {
        toDoUserService.register(NAME, PWD);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        String token = JWT.create()
                .withClaim("username", NAME)
                .sign(algorithm);
        Assertions.assertTrue(toDoUserService.validateToken(token));
    }

}
