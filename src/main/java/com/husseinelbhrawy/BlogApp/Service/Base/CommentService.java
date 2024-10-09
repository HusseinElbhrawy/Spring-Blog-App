package com.husseinelbhrawy.BlogApp.Service.Base;

import com.husseinelbhrawy.BlogApp.Payload.CommentDTO;

public interface CommentService {

    CommentDTO createComment(long postId, CommentDTO commentDTO);

    void deleteComment(long postId, long commentId);

    CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO);

    CommentDTO getCommentById(long postId, long commentId);

    Iterable<CommentDTO> getAllCommentsByPostId(long postId);
}
