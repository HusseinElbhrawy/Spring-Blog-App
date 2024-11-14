package com.husseinelbhrawy.BlogApp.Service.Base;


import com.husseinelbhrawy.BlogApp.Payload.PostDTO;
import com.husseinelbhrawy.BlogApp.Payload.PostResponse;

import java.util.List;


public interface PostServices {

    PostDTO createPost(PostDTO postDTO);

    PostResponse getAllPosts(int pageNumber , int pageSize ,String sortBy , String sortDirection);

    PostDTO getPostById(long id);

    PostDTO updatePost(PostDTO postDTO, long id);

    String deletePost(long id);

    List<PostDTO> findByCategoryId(long categoryId);
}

