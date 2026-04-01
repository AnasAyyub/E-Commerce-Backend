package com.store.services;

import com.store.dtos.*;
import com.store.models.Token;

public interface UserService {

    SignupResponseDTO registerUser(SignupDTO registerRequest);

    Token login(LoginDTO loginDTO);

    boolean validate(TokenDTO token);
}
