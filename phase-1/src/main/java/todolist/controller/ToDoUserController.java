package todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todolist.model.LoginRequest;
import todolist.service.PasswordMismatchException;
import todolist.service.ToDoUserService;

@RestController
@RequestMapping("/user")
public class ToDoUserController {
    @Autowired
    ToDoUserService toDoUserService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequest request) {
        boolean result = toDoUserService.register(request.getUsername(), request.getPassword());
        if (result) {
            return ResponseEntity.ok("User registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            String jwt = toDoUserService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok().body(jwt);
        } catch (PasswordMismatchException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
