package com.example.demo.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotEmpty(message = "Category Name should not be empty")
	@NotBlank
	@Size(min = 3, message = "Minimum size of category name is 3")
	private String categoryName;
	
	@NotBlank
	@Size(min = 4, message = "Minimum size of category desc is 3")
	private String categoryDescription;

}
