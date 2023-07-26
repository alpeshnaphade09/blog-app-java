package com.example.demo.service;

import java.util.List;

import com.example.demo.payload.CategoryDto;

public interface CategoryService {
	
	CategoryDto createCategory(CategoryDto cDto);
	CategoryDto updateCategory(CategoryDto cDto, Integer cId);
	void deleteCategory(Integer cId);
	CategoryDto getCategoryById(Integer cId);
	List<CategoryDto> getAllCategories();

}
