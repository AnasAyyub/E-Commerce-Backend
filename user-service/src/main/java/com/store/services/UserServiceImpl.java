package com.store.services;

import com.store.dtos.*;
import com.store.exceptions.EmailAlreadyExistsException;
import com.store.exceptions.InvalidTokenException;
import com.store.exceptions.UserNotFoundException;
import com.store.models.Role;
import com.store.models.Token;
import com.store.models.User;
import com.store.repositories.RoleRepository;
import com.store.repositories.TokenRepository;
import com.store.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private RoleRepository roleRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder,UserRepository userRepository,TokenRepository tokenRepository,RoleRepository roleRepository) {
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
        this.tokenRepository=tokenRepository;
        this.roleRepository=roleRepository;
    }


    //Signing Up
    public SignupResponseDTO registerUser(SignupDTO signupDTO){
        if (userRepository.existsByEmail(signupDTO.getEmail())){
            throw new EmailAlreadyExistsException("Email Already Exists");
        }
        User newUser=convertToUser(signupDTO);
        Role role=roleRepository.findByName("ROLE_USER");
        newUser.setRoles(List.of(role));
        userRepository.save(newUser);
        return convertToSignupResponseDTO(newUser);
    }

    public Token login(LoginDTO loginDTO){
        Optional<User> u=userRepository.findByEmail(loginDTO.getEmail());

        if (u.isEmpty()){
            throw new UserNotFoundException("User Not Found with "+loginDTO.getEmail());
        }
        User savedUser=u.get();
        if (!passwordEncoder.matches(loginDTO.getPassword(),savedUser.getHashedPassword())){
            return null;
        }
        return tokenRepository.save(generateToken(savedUser));
    }

    @Override
    public boolean validate(TokenDTO token) {
        // 1. Fetch by the random string value
        Token fetchedToken = tokenRepository.findByValue(token.getValue())
                .orElseThrow(()->new InvalidTokenException("Invalid Token"));

        // 2. Check if it was manually revoked/deleted
        if (fetchedToken.isDeleted()) {
            return false;
        }

        // 3. Check Expiry: Is today AFTER the expiry date?
        // Using !isBefore ensures it stays valid on the actual day of expiration.
        if (LocalDate.now().isAfter(fetchedToken.getExpiryDate())) {
            return false;
        }
        return true;
    }


    public Token generateToken(User savedUser){
        LocalDate currentDate=LocalDate.now();
        LocalDate thirtyDaysLater=currentDate.plusDays(30);
        Token t=new Token();
        t.setExpiryDate(thirtyDaysLater);
        t.setUser(savedUser);
        t.setValue(UUID.randomUUID().toString());
        return t;
    }
    public User convertToUser(SignupDTO registerRequest){
        User u=new User();
        u.setName(registerRequest.getName());
        u.setEmail(registerRequest.getEmail());
        u.setHashedPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return u;
    }

//    public UserDTO convertToUserDTO(User u){
//        UserDTO userDTO=new UserDTO();
//        userDTO.setName(u.getName());
//        userDTO.setEmail(u.getEmail());
//        return userDTO;
//    }


    public SignupResponseDTO convertToSignupResponseDTO(User newUser){

        SignupResponseDTO signupResponseDTO=new SignupResponseDTO();
        signupResponseDTO.setId(newUser.getId());
        signupResponseDTO.setMessage("User created successfully");

        return signupResponseDTO;
    }
}
