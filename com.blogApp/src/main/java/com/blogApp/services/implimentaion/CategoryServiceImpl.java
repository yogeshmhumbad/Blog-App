package com.blogApp.services.implimentaion;

import com.blogApp.entities.Category;
import com.blogApp.exceptions.ResourceNotFoundException;
import com.blogApp.payloads.CategoryDto;
import com.blogApp.repositories.CategoryRepository;
import com.blogApp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category category1 = this.categoryRepository.save(category);
        return this.modelMapper.map(category1, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("category","category Id",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category category1 = this.categoryRepository.save(category);
        return this.modelMapper.map(category1, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("category","category Id",categoryId));
    this.categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("category","category Id",categoryId));

        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = this.categoryRepository.findAll();
    return categories.stream()
            .map((category) -> this
                    .modelMapper.map(category, CategoryDto.class))
            .collect(Collectors.toList());
    }
}
