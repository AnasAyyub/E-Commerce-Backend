package com.store.controllers;

import com.store.dtos.TokenDTO;
import com.store.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    public UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
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
