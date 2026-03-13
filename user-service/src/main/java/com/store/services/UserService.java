package com.store.services;

import com.store.dtos.LoginDTO;
import com.store.dtos.RegisterRequestDTO;
import com.store.dtos.TokenDTO;
import com.store.dtos.UserDTO;
import com.store.models.Token;
import com.store.models.User;

public interface UserService {

    UserDTO registerUser(RegisterRequestDTO registerRequest);

    Token login(LoginDTO loginDTO);

    boolean validate(TokenDTO token);
}
