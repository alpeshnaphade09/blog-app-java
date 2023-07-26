package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payload.CategoryDto;
import com.example.demo.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository repo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto createCategory(CategoryDto cDto) {
		Category category = repo.save(mapper.map(cDto, Category.class));
		return mapper.map(category, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto cDto, Integer cId) {
		Category category = repo.findById(cId).orElseThrow(() -> new ResourceNotFoundException("category", "Id", cId));
		category.setCategoryName(cDto.getCategoryName());
		category.setCategoryDescription(cDto.getCategoryDescription());
		Category saveCat = repo.save(category);
		return mapper.map(saveCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer cId) {
		Category category = repo.findById(cId).orElseThrow(() -> new ResourceNotFoundException("category", "Id", cId));
		repo.delete(category);
	}

	@Override
	public CategoryDto getCategoryById(Integer cId) {
		Category category = repo.findById(cId).orElseThrow(() -> new ResourceNotFoundException("category", "Id", cId));
		return mapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> catList = repo.findAll();
		return catList.stream().map(cat -> mapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
	}

}
