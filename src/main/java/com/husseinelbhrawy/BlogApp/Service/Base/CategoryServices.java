package com.husseinelbhrawy.BlogApp.Service.Base;

import com.husseinelbhrawy.BlogApp.Payload.CategoryDTO;

import java.util.List;

public interface CategoryServices {



    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategoryById(long id);

    List<CategoryDTO> getAllCategories();

    CategoryDTO updateCategory(CategoryDTO categoryDTO, long id);

    void deleteCategory(long id);

}
