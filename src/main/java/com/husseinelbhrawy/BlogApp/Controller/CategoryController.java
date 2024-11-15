package com.husseinelbhrawy.BlogApp.Controller;

import com.husseinelbhrawy.BlogApp.Payload.CategoryDTO;
import com.husseinelbhrawy.BlogApp.Service.Base.CategoryServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServices categoryServices;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        return  ResponseEntity.ok(categoryServices.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getAllCategories(@PathVariable long id){
        return  ResponseEntity.ok(categoryServices.getCategoryById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(categoryServices.createCategory(categoryDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO , @PathVariable long id){
        return ResponseEntity.ok(categoryServices.updateCategory(categoryDTO , id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable long id){
        categoryServices.deleteCategory(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Category Deleted Successfully");
        return ResponseEntity.ok(response);
    }


}
