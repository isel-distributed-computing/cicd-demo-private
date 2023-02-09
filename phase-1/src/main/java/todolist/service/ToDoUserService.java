package todolist.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todolist.model.User;
import todolist.repository.UserRepository;

import java.util.HashMap;

@Service
public class ToDoUserService {
    HashMap<String, String> passwords = new HashMap<>();

    @Autowired
    private UserRepository userRepository;

    Dotenv dotenv;

    public ToDoUserService() {
        dotenv = Dotenv.configure().load();
    }

    public boolean register(String username, String pwd) {
        // mock DB
        if (passwords.containsKey(username))
            return false;
        passwords.put(username, pwd);
        // real DB
        userRepository.save(new User(username, pwd, "dummy salt"));
        return true;
    }

    public String login(String username, String pwd) throws PasswordMismatchException {
        if (!passwords.get(username).equals(pwd)) {
            throw new PasswordMismatchException();
        }
        Algorithm algorithm = Algorithm.HMAC256(dotenv.get("JWT_SECRET"));
        return JWT.create()
                .withClaim("username", username)
                .sign(algorithm);
    }

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(dotenv.get("JWT_SECRET"));
            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token);
            String sub = jwt.getClaim("username").asString();
            return passwords.containsKey(sub);
        } catch (Exception e) {
            return false;
        }
    }
}
