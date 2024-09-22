package com.husseinelbhrawy.BlogApp.Service.Implementation;

import com.husseinelbhrawy.BlogApp.Entity.Post;
import com.husseinelbhrawy.BlogApp.Payload.PostDTO;
import com.husseinelbhrawy.BlogApp.Repository.PostRepository;
import com.husseinelbhrawy.BlogApp.Service.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServicesImplementation implements PostServices{
    private final PostRepository postRepository;

    @Autowired
    public PostServicesImplementation(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = mapToEntity(postDTO);
        return  mapToDTO(postRepository.save(post));
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return  postRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).get();
        return mapToDTO(post);
    }

    private PostDTO mapToDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());
        return postDTO;
    }

    private Post mapToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        return  post;

    }
}
