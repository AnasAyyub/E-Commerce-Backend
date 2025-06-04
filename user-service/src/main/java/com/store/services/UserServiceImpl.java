package com.store.services;

import com.store.dtos.RegisterRequest;
import com.store.models.User;
import com.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder,UserRepository userRepository){
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
    }
    public void registerUser(RegisterRequest registerRequest){
        userRepository.save(convertToUser(registerRequest));
    }

    public User convertToUser(RegisterRequest registerRequest){
        User u=new User();
        u.setEmail(registerRequest.getEmail());
        u.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        u.setRole("Role_User");
        return u;
    }
}
