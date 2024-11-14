package com.husseinelbhrawy.BlogApp.Auth.Controller;

import com.husseinelbhrawy.BlogApp.Auth.DTO.AuthResponse;
import com.husseinelbhrawy.BlogApp.Auth.DTO.LoginRequest;
import com.husseinelbhrawy.BlogApp.Auth.DTO.RegisterRequest;
import com.husseinelbhrawy.BlogApp.Auth.Services.Base.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping(value = {"/login" , "/signin"})  //? use /login or /signin
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginParam){

        return  new ResponseEntity<>(authService.login(loginParam), HttpStatus.OK);

    }


    @PostMapping(value = {"/register" , "/signup"})  //? use /register or /signup
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerParam){
        return  new ResponseEntity<>(authService.register(registerParam), HttpStatus.CREATED);
    }

}
