package com.husseinelbhrawy.BlogApp.Payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Comments" ,hidden = true)
public class CommentDTO {
    private  long id;

    @NotEmpty(message = "Name is required")
    private  String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Please enter a valid email")
    private  String email;

    @NotEmpty(message = "Body is required")
    @NotBlank(message = "Body is required")
    @Size(min = 10 , message = "Comment Body Should have at least 10 characters")
    private  String body;
}
