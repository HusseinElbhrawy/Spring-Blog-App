package com.husseinelbhrawy.BlogApp.Auth.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.husseinelbhrawy.BlogApp.Auth.Entity.Roles;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private  String name;
    private  String username;
    private  String email;
    private  String accessToken;
    private Roles roles;
}


