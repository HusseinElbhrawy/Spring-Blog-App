package com.husseinelbhrawy.BlogApp.Service.Base;

import com.husseinelbhrawy.BlogApp.Payload.LoginDTO;
import com.husseinelbhrawy.BlogApp.Payload.RegisterDTO;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public interface AuthService {

    ResponseEntity<Object> login(LoginDTO loginDTO);
    ResponseEntity<Object> register(RegisterDTO registerDTO);


}
