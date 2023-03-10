package todolist.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import todolist.model.User;
import todolist.repository.UserRepository;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// about MockitoExtension
// https://www.infoworld.com/article/3537563/junit-5-tutorial-part-1-unit-testing-with-junit-5-mockito-and-hamcrest.html?page=3
//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ToDoUserServiceTests {
    @Mock
    private UserRepository userRepository;

    private ToDoUserService toDoUserService;

    public static final String NAME = "name";
    public static final String PWD = "pwd";

    @Value("${JWT_SECRET}")
    private String SECRET;

    @BeforeEach
    public void setup() {
        toDoUserService = new ToDoUserService(SECRET, userRepository);
    }

    @Test
    public void testRegister_withNonExistingUsername_returnsTrue() throws NoSuchAlgorithmException {
        boolean result = toDoUserService.register("newUsername", "aa");

        assertEquals(true, result);
    }

    @Test
    public void testRegister_withExistingUsername_returnsFalse() throws NoSuchAlgorithmException {
        when(userRepository.findByUsername("existingUsername"))
                .thenReturn(Optional.of(new User("existingUsername", "password")));

        boolean result = toDoUserService.register("existingUsername", "password");

        assertEquals(false, result);
    }

    @Test
    public void testLogin_withCorrectCredentials_shouldReturnJWT() throws NoSuchAlgorithmException, PasswordMismatchException, UnknownUserException {
        when(userRepository.findByUsername(any(String.class)))
                .thenReturn(Optional.of(new User("username", new BCryptPasswordEncoder().encode("password"))));

        String jwt = toDoUserService.login("username", "password");

        assertNotNull(jwt);
    }

    @Test
    public void testValidateToken() throws NoSuchAlgorithmException {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        String token = JWT.create()
                .withClaim("username", "existingUsername")
                .sign(algorithm);
        assertTrue(toDoUserService.validateToken(token));
    }

}
