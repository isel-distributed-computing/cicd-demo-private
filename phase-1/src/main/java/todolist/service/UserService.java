package todolist.service;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.HashMap;

@Service
public class UserService {
    HashMap<String, String> pwds;

    public boolean register(String username, String pwd) {
        if (pwds.containsKey(username))
            return false;
        pwds.put(username, pwd);
        return true;
    }

    public String login(String username, String pwd) throws PasswordMismatchException {
        if (!pwds.get(username).equals(pwd)) {
            throw new PasswordMismatchException();
        }
        String token = Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .signWith(SignatureAlgorithm.HS256, "secret")
            .compact();
        return token;
    }

}
