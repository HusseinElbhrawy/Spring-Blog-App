package com.husseinelbhrawy.BlogApp.Payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthResponse {

    private  String accessToken;
    private  final String tokenType = "Bearer";
}


