package com.store.controllers;

import com.store.dtos.LoginDTO;
import com.store.dtos.RegisterRequestDTO;
import com.store.dtos.TokenDTO;
import com.store.dtos.UserDTO;
import com.store.models.Token;
import com.store.models.User;
import com.store.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    public UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    //Sign up
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequest){
        UserDTO createdUser=userService.registerUser(registerRequest);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginDTO loginDTO){
        return userService.login(loginDTO);
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validate(@RequestBody TokenDTO token){
        if (userService.validate(token)){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

}
