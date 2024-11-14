package com.husseinelbhrawy.BlogApp.Repository;

import com.husseinelbhrawy.BlogApp.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


//@RepositoryRestResource(path = "posts")
public interface PostRepository extends JpaRepository<Post , Long> {

    Optional<List<Post>> findByCategoryId(long categoryId);
}
