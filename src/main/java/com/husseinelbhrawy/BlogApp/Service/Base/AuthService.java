package com.husseinelbhrawy.BlogApp.Service.Base;

import com.husseinelbhrawy.BlogApp.Payload.LoginDTO;
import com.husseinelbhrawy.BlogApp.Payload.RegisterDTO;
import org.springframework.http.ResponseEntity;



public interface AuthService {

    String login(LoginDTO loginDTO);
    ResponseEntity<Object> register(RegisterDTO registerDTO);


}
