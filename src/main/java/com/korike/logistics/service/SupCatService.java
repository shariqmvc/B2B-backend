package com.korike.logistics.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.korike.logistics.entity.SuperCategory;
import com.korike.logistics.model.SuperCategoryDto;

@Service
public interface SupCatService {

	public SuperCategoryDto createSupCat(SuperCategoryDto supCat);
	public SuperCategoryDto updateSupCat(SuperCategoryDto supCat, SuperCategory fetchedSupCat);
	public List<SuperCategoryDto> getSupCatByFilter(SuperCategoryDto supCat);
	
}
