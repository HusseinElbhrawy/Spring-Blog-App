package com.husseinelbhrawy.BlogApp.Payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.util.Set;

@Data
public class PostDTO {
    private  long id;

    @Size(min = 2 , message = "Post Title Should have at least 2 characters")
    @NotEmpty
    private  String title;


    @Size(min = 10 , message = "Post Description Should have at least 10 characters")
    @NotEmpty
    private  String description;

    private  String content;
    private Set<CommentDTO> comments;
}
