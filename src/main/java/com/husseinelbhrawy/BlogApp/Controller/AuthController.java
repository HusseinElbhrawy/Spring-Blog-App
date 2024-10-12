package com.husseinelbhrawy.BlogApp.Controller;

import com.husseinelbhrawy.BlogApp.Payload.JWTAuthResponse;
import com.husseinelbhrawy.BlogApp.Payload.LoginDTO;
import com.husseinelbhrawy.BlogApp.Payload.RegisterDTO;
import com.husseinelbhrawy.BlogApp.Service.Base.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login" , "/signin"})  //? use /login or /signin
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginParam){
        String token = authService.login(loginParam);


        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return  new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);

    }


    @PostMapping(value = {"/register" , "/signup"})  //? use /register or /signup
    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerParam){
        return  new ResponseEntity<>(authService.register(registerParam), HttpStatus.CREATED);
    }

}
