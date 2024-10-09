package com.husseinelbhrawy.BlogApp.Controller;

import com.husseinelbhrawy.BlogApp.Payload.CommentDTO;
import com.husseinelbhrawy.BlogApp.Service.Base.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts/{postId}/comments")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@PathVariable(value = "postId") Long postId,@RequestBody CommentDTO commentDTO) {
        return  new ResponseEntity<>(commentService.createComment(postId,commentDTO) , HttpStatus.CREATED);
    }

    @GetMapping
    public  ResponseEntity<Iterable<CommentDTO>> getAllCommentsByPostId(@PathVariable(value = "postId") Long postId) {
        return  new ResponseEntity<>(commentService.getAllCommentsByPostId(postId) , HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public  ResponseEntity<CommentDTO> getCommentById(@PathVariable(value = "postId") Long postId,@PathVariable(value = "commentId") Long commentId) {
        return  new ResponseEntity<>(commentService.getCommentById(postId,commentId) , HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(value = "postId") Long postId,@PathVariable(value = "commentId") Long commentId,@RequestBody CommentDTO commentDTO) {
        return  new ResponseEntity<>(commentService.updateComment(postId,commentId,commentDTO) , HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public  ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,@PathVariable(value = "commentId") Long commentId) {
        commentService.deleteComment(postId , commentId);
        return  new ResponseEntity<>("Comment Has Been Deleted Successfully ✅" , HttpStatus.OK);
    }
}
