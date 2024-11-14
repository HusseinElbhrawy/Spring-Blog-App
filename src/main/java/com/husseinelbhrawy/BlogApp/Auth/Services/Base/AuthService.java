package com.husseinelbhrawy.BlogApp.Auth.Services.Base;

import com.husseinelbhrawy.BlogApp.Auth.DTO.AuthResponse;
import com.husseinelbhrawy.BlogApp.Auth.DTO.LoginRequest;
import com.husseinelbhrawy.BlogApp.Auth.DTO.RegisterRequest;


public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);


}
