package com.husseinelbhrawy.BlogApp.Service.Implementation;

import com.husseinelbhrawy.BlogApp.Exceptions.BlogAPIException;
import com.husseinelbhrawy.BlogApp.Payload.CategoryDTO;
import com.husseinelbhrawy.BlogApp.Repository.CategoryRepository;
import com.husseinelbhrawy.BlogApp.Service.Base.CategoryServices;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplementation implements CategoryServices {
    private  final CategoryRepository categoryRepository;
    private  final ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        var category = modelMapper.map(categoryDTO, com.husseinelbhrawy.BlogApp.Entity.Category.class);
        return modelMapper.map(categoryRepository.save(category), CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategoryById(long id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new BlogAPIException(HttpStatus.NOT_FOUND ,  "Category with id : " + id + " Not Found"));
        return modelMapper.map(category , CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        var categories = categoryRepository.findAll();
        return categories.stream().map(category -> modelMapper.map(category , CategoryDTO.class)).toList();
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, long id) {
        var category = getCategoryById(id);
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        return createCategory(category);
    }

    @Override
    public void deleteCategory(long id) {
        var category = getCategoryById(id); //! if not exist it will throw an BlogApiException
        categoryRepository.deleteById(id);
    }
}
