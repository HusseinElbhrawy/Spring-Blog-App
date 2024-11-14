package com.husseinelbhrawy.BlogApp.Auth.DTO;

import lombok.*;
import org.hibernate.annotations.SecondaryRow;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private  String name;
    private  String username;
    private  String email;
    private  String password;
}
