package com.husseinelbhrawy.BlogApp.Service;


import com.husseinelbhrawy.BlogApp.Payload.PostDTO;

import java.util.List;

public interface PostServices {

    PostDTO createPost(PostDTO postDTO);

    List<PostDTO> getAllPosts();

    PostDTO getPostById(long id);
}

