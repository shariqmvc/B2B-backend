package com.korike.logistics.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.korike.logistics.entity.Category;
import com.korike.logistics.entity.SuperCategory;
import com.korike.logistics.model.CategoryDto;

@Service
public interface CatService {

	public CategoryDto createCat(CategoryDto catDto, SuperCategory fetchedSupCat);
	public CategoryDto updateCat(CategoryDto catDto, SuperCategory fetchedSupCat, Category fetchedCat);
	public List<CategoryDto> getCategoryByFilter(CategoryDto cat);
}
