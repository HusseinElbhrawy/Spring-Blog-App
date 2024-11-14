package com.husseinelbhrawy.BlogApp.Repository;

import com.husseinelbhrawy.BlogApp.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category , Long> {
}
