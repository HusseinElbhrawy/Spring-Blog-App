package com.husseinelbhrawy.BlogApp.Service.Implementation;

import com.husseinelbhrawy.BlogApp.Entity.Comment;
import com.husseinelbhrawy.BlogApp.Entity.Post;
import com.husseinelbhrawy.BlogApp.Exceptions.ResourceNotFoundException;
import com.husseinelbhrawy.BlogApp.Payload.CommentDTO;
import com.husseinelbhrawy.BlogApp.Payload.PostDTO;
import com.husseinelbhrawy.BlogApp.Payload.PostResponse;
import com.husseinelbhrawy.BlogApp.Repository.CommentRepository;
import com.husseinelbhrawy.BlogApp.Repository.PostRepository;
import com.husseinelbhrawy.BlogApp.Service.Base.PostServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServicesImplementation implements PostServices{
    private final PostRepository postRepository;
    private  final CommentRepository commentRepository;
    private  final ModelMapper modelMapper;


    @Autowired
    public PostServicesImplementation(PostRepository postRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
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
        System.out.println("Post Data  : " + optionalPost.getComments());
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
        System.out.println("{mapToDTO} : POST Comments" + post.getComments()    );
        PostDTO postDTO = modelMapper.map(post , PostDTO.class);
        System.out.println("{mapToDTO} : PostDTO Comments" + postDTO.getComments());
        return  postDTO;
    }

    private Post mapToEntity(PostDTO postDTO){
        System.out.println("{mapToDTO} : PostDTO Comments" + postDTO.getComments());
        Post post = modelMapper.map(postDTO , Post.class);
        System.out.println("{mapToDTO} : POST Comments" + post.getComments()    );

        return  post;
    }
}
