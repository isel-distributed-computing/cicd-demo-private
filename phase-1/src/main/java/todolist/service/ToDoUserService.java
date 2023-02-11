package todolist.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import todolist.model.User;
import todolist.repository.UserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@Service
public class ToDoUserService {
    private String secret;

    private UserRepository userRepository;

    @Autowired
    public ToDoUserService(@Value("${JWT_SECRET}") String secret, @Autowired UserRepository userRepository) {
        this.secret = secret;
        this.userRepository = userRepository;
    }

    public boolean register(String username, String pwd) throws NoSuchAlgorithmException {
        // real DB
        if (userRepository.findByUsername(username).isPresent())
            return false;
        // -- securely save password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwdSalted = encoder.encode(pwd);
        // -- save to DB
        userRepository.save(new User(username, pwdSalted));
        return true;
    }

    public String login(String username, String pwd) throws PasswordMismatchException, UnknownUserException, NoSuchAlgorithmException {
        // check pwd
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent())
            throw new UnknownUserException();
        // check pwd
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(pwd, user.get().getSaltedPwd()))
            throw new PasswordMismatchException();
        // create JWT
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim("username", username)
                .sign(algorithm);
    }

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token);
            String sub = jwt.getClaim("username").asString();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
