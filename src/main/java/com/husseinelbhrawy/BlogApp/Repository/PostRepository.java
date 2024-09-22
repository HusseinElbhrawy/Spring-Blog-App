package com.husseinelbhrawy.BlogApp.Repository;

import com.husseinelbhrawy.BlogApp.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;


//@RepositoryRestResource(path = "posts")
public interface PostRepository extends JpaRepository<Post , Long> {
}
