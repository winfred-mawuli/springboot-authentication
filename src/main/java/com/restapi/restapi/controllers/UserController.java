package com.restapi.restapi.controllers;


import com.restapi.restapi.dto.UserDTO;
import com.restapi.restapi.models.User;
import com.restapi.restapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor

public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getUsers();
//    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
    @PostMapping
    public ResponseEntity<User> createUser (@RequestBody User user) {
        User newUser = userService.creatUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
