package com.husseinelbhrawy.BlogApp.Controller;

import com.husseinelbhrawy.BlogApp.Payload.PostDTO;
import com.husseinelbhrawy.BlogApp.Payload.PostResponse;
import com.husseinelbhrawy.BlogApp.Service.Base.PostServices;
import com.husseinelbhrawy.BlogApp.Utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
@Tag(
        name = "Posts API",
        description = "This API for CRUD operation on Posts"
)
public class PostController {

    private  final PostServices postServices;

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping
    @Operation(
        summary = "Create new Post",
        description = "Create new Post",
        extensions = {
                @Extension(
                        name = "Authorization",
                        properties = {
                                @ExtensionProperty(name = "type", value = "apiKey"),
                                @ExtensionProperty(name = "name", value = "Authorization"),
                                @ExtensionProperty(name = "in", value = "header")
                        }
                )
        },
            hidden = false,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PostDTO.class)
                    )
            )

    )
    @ApiResponse(responseCode = "201", description = "Created")
    public ResponseEntity<PostDTO> createNewPost(@Valid @RequestBody PostDTO postDTO){
        PostDTO postDTO1 = postServices.createPost(postDTO);
        return new ResponseEntity<>(postDTO1 , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo" , defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize" , defaultValue = AppConstants.DEFAULT_PAGE_SIZE , required = false) int pageSize,
            @RequestParam(value = "sortBy" , defaultValue = AppConstants.DEFAULT_SORT_BY , required = false) String sortBy,
            @RequestParam(value = "sortDirection" , defaultValue = AppConstants.DEFAULT_SORT_DIRECTION , required = false) String sortDirection
    ){
        return ResponseEntity.ok(postServices.getAllPosts(pageNo , pageSize , sortBy , sortDirection));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("id") long id){
        return ResponseEntity.ok(postServices.getPostById(id));
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO , @PathVariable("id") long id){
        return ResponseEntity.ok(postServices.updatePost(postDTO , id));
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        return ResponseEntity.ok(postServices.deletePost(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDTO>> findByCategoryId(@PathVariable("id") long id){
        return ResponseEntity.ok(postServices.findByCategoryId(id));
    }




}
