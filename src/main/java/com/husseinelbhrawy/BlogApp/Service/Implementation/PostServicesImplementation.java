package com.husseinelbhrawy.BlogApp.Service.Implementation;

import com.husseinelbhrawy.BlogApp.Entity.Category;
import com.husseinelbhrawy.BlogApp.Entity.Post;
import com.husseinelbhrawy.BlogApp.Exceptions.BlogAPIException;
import com.husseinelbhrawy.BlogApp.Exceptions.ResourceNotFoundException;
import com.husseinelbhrawy.BlogApp.Payload.PostDTO;
import com.husseinelbhrawy.BlogApp.Payload.PostResponse;
import com.husseinelbhrawy.BlogApp.Repository.CategoryRepository;
import com.husseinelbhrawy.BlogApp.Repository.CommentRepository;
import com.husseinelbhrawy.BlogApp.Repository.PostRepository;
import com.husseinelbhrawy.BlogApp.Service.Base.PostServices;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServicesImplementation implements PostServices{
    private  final PostRepository postRepository;
    private  final CommentRepository commentRepository;
    private  final ModelMapper modelMapper;
    private  final CategoryRepository categoryRepository;


    private Category getCategoryById(long id){
        return  categoryRepository.findById(id).orElseThrow(() -> new BlogAPIException(HttpStatus.NOT_FOUND , "Category With Id " + id + " Not Found"));
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = mapToEntity(postDTO);
        post.setCategory(getCategoryById(postDTO.getCategoryId()));
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



        return PostResponse.builder()
                .content(content)
                .last(postPage.isLast())
                .pageNo(pageNumber)
                .pageSize(pageSize)
                .totalPages(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .build();
    }




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
        post.setCategory(getCategoryById(postDTO.getCategoryId()));
        //! Save Post
        return  mapToDTO(postRepository.save(post));
    }

    @Override
    public String deletePost(long id) {
        Post post =  postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Delete" ,   "ID:"  ,  id));
        postRepository.delete(post);
        return "Post Deleted Successfully With ID:" + id + " ✅ ";
    }

    @Override
    public List<PostDTO> findByCategoryId(long categoryId) {
        var category = getCategoryById(categoryId);
        List<Post> allPosts = postRepository.findByCategoryId(category.getId()).orElseThrow(() -> new ResourceNotFoundException("Post" ,   "ID:"  ,  categoryId));
        return allPosts.stream().map(this::mapToDTO).toList();

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
