package com.husseinelbhrawy.BlogApp.Payload;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorDetails {
    private Date timeStamp;
    private  String message;
    private  String details;
    private  int statusCode;
}
