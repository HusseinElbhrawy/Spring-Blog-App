package com.husseinelbhrawy.BlogApp.Auth.DTO;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private  String username;
    private  String password;

}