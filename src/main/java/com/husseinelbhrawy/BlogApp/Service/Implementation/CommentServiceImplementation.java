package com.husseinelbhrawy.BlogApp.Service.Implementation;

import com.husseinelbhrawy.BlogApp.Entity.Comment;
import com.husseinelbhrawy.BlogApp.Entity.Post;
import com.husseinelbhrawy.BlogApp.Exceptions.BlogAPIException;
import com.husseinelbhrawy.BlogApp.Exceptions.ResourceNotFoundException;
import com.husseinelbhrawy.BlogApp.Payload.CommentDTO;
import com.husseinelbhrawy.BlogApp.Repository.CommentRepository;
import com.husseinelbhrawy.BlogApp.Repository.PostRepository;
import com.husseinelbhrawy.BlogApp.Service.Base.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImplementation implements CommentService {

    private final CommentRepository commentRepository;
    private  final  PostRepository postRepository;
    private  final ModelMapper modelMapper;


    @Autowired
    public CommentServiceImplementation(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = mapToEntity(commentDTO);

        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post" ,   "ID:"  ,  postId));

        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);

    }

    @Override
    public void deleteComment(long postId, long commentId) {
        //! GET Post
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post" ,   "ID:"  ,  postId));
        //! GET Comment
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment" ,   "ID:"  ,  commentId));

        if (comment.getPost().getId()!= post.getId()){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST , "This Comment not belong to this Post , POST Id : " + postId);
        }

        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO) {
        //! GET Post
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "ID:", postId));
        //! GET Comment
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "ID:", commentId));


        if (comment.getPost().getId() != post.getId()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "This Comment not belong to this Post , POST Id : " + postId);
        } else {
            comment.setBody(commentDTO.getBody());
            comment.setName(commentDTO.getName());
            comment.setEmail(commentDTO.getEmail());
        }
        Comment updatedComment =  commentRepository.save(comment);

        return mapToDTO(updatedComment);
    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {
        //! GET Post
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post" ,   "ID:"  ,  postId));
        //! GET Comment
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment" ,   "ID:"  ,  commentId));

        if (comment.getPost().getId()!= post.getId()){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST , "This Comment not belong to this Post , POST Id : " + postId);
        }

        return mapToDTO(comment);
    }

    @Override
    public Iterable<CommentDTO> getAllCommentsByPostId(long postId) {
        List<Comment> comment =  commentRepository.findByPostId(postId);
        return comment.stream().map(this::mapToDTO).toList();
    }


    private CommentDTO mapToDTO(Comment comment){
        return modelMapper.map(comment , CommentDTO.class);
    }

    private Comment mapToEntity(CommentDTO commentDTO){
        return modelMapper.map(commentDTO, Comment.class);
    }
}
