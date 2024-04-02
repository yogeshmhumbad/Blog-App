package com.blogApp.controllers;

import com.blogApp.payloads.ApiResponse;
import com.blogApp.payloads.CategoryDto;
import com.blogApp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestControllerAdvice
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted",true), HttpStatus.OK);
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId){

        return new ResponseEntity<CategoryDto>(this.categoryService.getCategory(categoryId),HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories(){
        List<CategoryDto> categories = this.categoryService.getAllCategory();

        return ResponseEntity.ok(categories);
    }

}
