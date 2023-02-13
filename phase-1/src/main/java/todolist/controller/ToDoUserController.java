package todolist.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todolist.model.LoginRequest;
import todolist.service.PasswordMismatchException;
import todolist.service.ToDoUserService;
import todolist.service.UnknownUserException;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/user")
public class ToDoUserController {
    @Autowired
    ToDoUserService toDoUserService;

    @ApiOperation(value = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequest request) {
        boolean result = false;
        try {
            result = toDoUserService.register(request.getUsername(), request.getPassword());
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed");
        }
        return result ?
                ResponseEntity.ok("User registered successfully") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed");
    }

    @ApiOperation(value = "login a user and return a JWT")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            String jwt = toDoUserService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok().body(jwt);
        } catch (PasswordMismatchException | UnknownUserException | NoSuchAlgorithmException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
