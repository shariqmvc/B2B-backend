package com.korike.logistics.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.korike.logistics.entity.Category;
import com.korike.logistics.entity.SubCategory;
import com.korike.logistics.entity.SuperCategory;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.model.CategoryDto;
import com.korike.logistics.model.SubCategoryDto;
import com.korike.logistics.repository.CategoryRepository;
import com.korike.logistics.repository.SubCategoryRepository;
import com.korike.logistics.service.SubCatService;
import com.korike.logistics.util.CommonUtils;

@Service
public class SubCategoryServiceImpl implements SubCatService {

	private static Logger logger = Logger.getLogger(SubCategoryServiceImpl.class);

	@Autowired
	SubCategoryRepository subCatRepository;
	@Autowired
	CategoryRepository catRepository;

	@Override
	public SubCategoryDto createSubCategory(SubCategoryDto subCat, Category fetchedCat) {
		logger.info("Started createSubCategory() method ");

		SubCategory newSubCat = new SubCategory();
		newSubCat.setName(subCat.getName());
		newSubCat.setCategory(fetchedCat);
		newSubCat.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		newSubCat.setCreatedBy(CommonUtils.getCurrentUserName());
		newSubCat.setIsActive(true);

		try {
			newSubCat = subCatRepository.save(newSubCat);
			subCat.setSubCatId(newSubCat.getSubCatId());
		} catch (Exception exc) {
			logger.error("Exception occured in createSubCategory() method.");
			throw new KorikeException("Internal Server Error");
		}

		return subCat;
	}

	@Override
	public SubCategoryDto updateSubCategory(SubCategoryDto subCat, Category fetchedCat, SubCategory fetchedSubCat) {
		logger.info("Started updateSubCategory() method ");

		fetchedSubCat.setName(subCat.getName());
		fetchedSubCat.setCategory(fetchedCat);
		fetchedSubCat.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
		fetchedSubCat.setLastUpdatedBy(CommonUtils.getCurrentUserName());
		// newSubCat.setIsActive(true);

		try {
			fetchedSubCat = subCatRepository.save(fetchedSubCat);
			 subCat.setSubCatId(fetchedSubCat.getSubCatId());
		} catch (Exception exc) {
			logger.error("Exception occured in createSubCategory() method.");
			throw new KorikeException("Internal Server Error");
		}

		return subCat;
	}

	@Override
	public List<SubCategoryDto> getSubCategoryByFilter(SubCategoryDto subCatDto) {
		logger.info("Started getSubCategoryByFilter() method ");
		List<SubCategory> subCatList = new ArrayList();
		List<SubCategoryDto> subCatDtoList = new ArrayList();
		SubCategory subCat = null;

		// = new SuperCategory();
		if (subCatDto.getFilter() != null) {
			if (subCatDto.getFilter().getCatId() != null && subCatDto.getFilter().getCatId() != 0) {
				subCat = subCatRepository.findBySubCatId(subCatDto.getFilter().getCatId());
				if (subCat == null) {
					logger.error("Exception occured in getSubCategoryByFilter() method.");
					throw new KorikeException("Invalid subCatId");
				}
				subCatDto.setName(subCat.getName());
				subCatDto.setSubCatId(subCat.getSubCatId());
				subCatDto.setCatId(String.valueOf(subCat.getCategory().getCatId()));
				subCatDto.setCatName(subCat.getCategory().getName());
				subCatDtoList.add(subCatDto);
				// supCatList.add(supCat);
				return subCatDtoList;
			} else if (subCatDto.getFilter().getMasterCatId() != null && subCatDto.getFilter().getMasterCatId() != 0) {
				Optional<Category> fetchedCat = catRepository.findById(subCatDto.getFilter().getMasterCatId());
				if (!fetchedCat.isPresent()) {
					logger.error("Exception occured in getCategoryByFilter() method.");
					throw new KorikeException("Invalid catId");
				}

				return subCatRepository.getSubCatByCatId(fetchedCat.get().getCatId()).stream()

						.map(spc -> new SubCategoryDto(spc.getSubCatId(), spc.getName(),
								String.valueOf(spc.getCategory().getCatId()), spc.getCategory().getName()))
						.collect(Collectors.toList());

			} else {
				return subCatRepository.findAll().stream()
						.map(spc -> new SubCategoryDto(spc.getSubCatId(), spc.getName(),
								String.valueOf(spc.getCategory().getCatId()), spc.getCategory().getName()))
						.collect(Collectors.toList());
			}
		} else {
			logger.error("Exception occured in getSupCatByFilter() method.");
			throw new KorikeException("Invalid SupCatId");
		}

	}

}
