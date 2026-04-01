package com.store.controllers;

import com.store.dtos.LoginDTO;
import com.store.dtos.SignupDTO;
import com.store.dtos.SignupResponseDTO;
import com.store.dtos.UserDTO;
import com.store.models.Token;
import com.store.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupDTO signupDTO){
        SignupResponseDTO createdUser=userService.registerUser(signupDTO);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginDTO loginDTO){
        return userService.login(loginDTO);

    }
}
