package com.husseinelbhrawy.BlogApp.Payload;

import com.husseinelbhrawy.BlogApp.Entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
    private  long id;
    @NotBlank
    @Size(min = 2 , message = "Category Title Should have at least 2 characters")
    private String name;
    private  String description;
}
