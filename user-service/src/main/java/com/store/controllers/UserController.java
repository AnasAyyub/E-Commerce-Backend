package com.store.controllers;

import com.store.dtos.RegisterRequest;
import com.store.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    public UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        userService.registerUser(registerRequest);
        return ResponseEntity.ok("User Registered");
    }

}
