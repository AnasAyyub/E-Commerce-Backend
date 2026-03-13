package com.store.services;

import com.store.dtos.LoginDTO;
import com.store.dtos.RegisterRequestDTO;
import com.store.dtos.TokenDTO;
import com.store.dtos.UserDTO;
import com.store.exceptions.InvalidTokenException;
import com.store.exceptions.UserNotFoundException;
import com.store.models.Token;
import com.store.models.User;
import com.store.repositories.TokenRepository;
import com.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private TokenRepository tokenRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder,UserRepository userRepository,TokenRepository tokenRepository){
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
        this.tokenRepository=tokenRepository;
    }

    public UserDTO registerUser(RegisterRequestDTO registerRequest){
        User newUser=convertToUser(registerRequest);
        userRepository.save(newUser);
        return convertToUserDTO(newUser);
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
    public User convertToUser(RegisterRequestDTO registerRequest){
        User u=new User();
        u.setName(registerRequest.getName());
        u.setEmail(registerRequest.getEmail());
        u.setHashedPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return u;
    }

    public UserDTO convertToUserDTO(User u){
        UserDTO userDTO=new UserDTO();
        userDTO.setName(u.getName());
        userDTO.setEmail(u.getEmail());
        return userDTO;
    }
}
