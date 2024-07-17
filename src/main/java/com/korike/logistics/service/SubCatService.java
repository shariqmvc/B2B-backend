package com.korike.logistics.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.korike.logistics.entity.Category;
import com.korike.logistics.entity.SubCategory;
import com.korike.logistics.model.CategoryDto;
import com.korike.logistics.model.SubCategoryDto;

@Service
public interface SubCatService {

	public SubCategoryDto createSubCategory(SubCategoryDto subCat, Category fetchedCat);
	public SubCategoryDto updateSubCategory(SubCategoryDto subCat, Category fetchedCat, SubCategory fetchedSubCat);
	public List<SubCategoryDto> getSubCategoryByFilter(SubCategoryDto subCat);
}
