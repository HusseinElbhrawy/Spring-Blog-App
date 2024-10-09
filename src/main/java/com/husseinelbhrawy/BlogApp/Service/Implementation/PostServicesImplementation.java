package com.husseinelbhrawy.BlogApp.Service.Implementation;

import com.husseinelbhrawy.BlogApp.Entity.Post;
import com.husseinelbhrawy.BlogApp.Exceptions.ResourceNotFoundException;
import com.husseinelbhrawy.BlogApp.Payload.PostDTO;
import com.husseinelbhrawy.BlogApp.Payload.PostResponse;
import com.husseinelbhrawy.BlogApp.Repository.PostRepository;
import com.husseinelbhrawy.BlogApp.Service.Base.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PostResponse getAllPosts(int pageNumber , int pageSize ,String sortBy , String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber , pageSize , sort);
        Page<Post> postPage = postRepository.findAll(pageable);
        List<Post> listOfPost = postPage.getContent();


        List<PostDTO> content =   listOfPost.stream().map(this::mapToDTO).toList();

        PostResponse response = new PostResponse();
        response.setContent(content);
        response.setLast(postPage.isLast());
        response.setPageNo(pageNumber);
        response.setPageSize(pageSize);
        response.setTotalPages(postPage.getTotalPages());
        response.setTotalElements(postPage.getTotalElements());

        return response;
    }



//    @Override
//    public PostDTO getPostById(long id) {
//        Post post = postRepository.findById(id).get();
//        return mapToDTO(post);
//
//    }

//    @Override
//    public PostDTO getPostById(long id) {
//        Optional<Post> optionalPost = postRepository.findById(id);
//        if (optionalPost.isPresent()) {
//            Post post = optionalPost.get();
//            return mapToDTO(post);
//        } else {
//            throw new ResourceNotFoundException("Post" ,   "ID:"  ,  id);
//        }
//    }

    @Override
    public PostDTO getPostById(long id) {
        Post optionalPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post" ,   "ID:"  ,  id));
        return  mapToDTO(optionalPost);

    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        //! Get Old Post
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post" ,   "ID:"  ,  id));
        //! Update Post
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        //! Save Post
        return  mapToDTO(postRepository.save(post));
    }

    @Override
    public String deletePost(long id) {
        Post post =  postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Delete" ,   "ID:"  ,  id));
        postRepository.delete(post);
        return "Post Deleted Successfully With ID:" + id + " ✅ ";
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
