package com.korike.logistics.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.korike.logistics.entity.Category;
import com.korike.logistics.entity.SuperCategory;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.model.CategoryDto;
import com.korike.logistics.model.SuperCategoryDto;
import com.korike.logistics.repository.CategoryRepository;
import com.korike.logistics.repository.SuperCategoryRepository;
import com.korike.logistics.service.CatService;
import com.korike.logistics.util.CommonUtils;

@Service
public class CatServiceImpl implements CatService {

	private static Logger logger = Logger.getLogger(CatServiceImpl.class);

	@Autowired
	CategoryRepository catRepository;
	@Autowired
	SuperCategoryRepository supCatRepository;

	@Override
	public CategoryDto createCat(CategoryDto cat, SuperCategory fetchedSupCat) {
		logger.info("Started createCat() method ");

		Category newCat = new Category();
		newCat.setName(cat.getName());
		newCat.setSuperCategory(fetchedSupCat);
		newCat.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		newCat.setCreatedBy(CommonUtils.getCurrentUserName());
		newCat.setIsActive(true);

		try {
			newCat = catRepository.save(newCat);
			cat.setCatId(newCat.getCatId());
		} catch (Exception exc) {
			logger.error("Exception occured in createCat() method.");
			throw new KorikeException("Internal Server Error");
		}

		return cat;

	}

	@Override
	public CategoryDto updateCat(CategoryDto cat, SuperCategory fetchedSupCat, Category fetchedCat) {
		logger.info("Started updateCat() method ");

		fetchedCat.setName(cat.getName());
		fetchedCat.setSuperCategory(fetchedSupCat);
		fetchedCat.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
		fetchedCat.setLastUpdatedBy(CommonUtils.getCurrentUserName());
		fetchedCat.setIsActive(true);

		try {
			fetchedCat = catRepository.save(fetchedCat);
			// cat.setCatId(newCat.getCatId());
		} catch (Exception exc) {
			logger.error("Exception occured in updateCat() method.");
			throw new KorikeException("Internal Server Error");
		}

		return cat;
	}

	@Override
	public List<CategoryDto> getCategoryByFilter(CategoryDto catDto) {
		List<Category> supCatList = new ArrayList();
		List<CategoryDto> catDtoList = new ArrayList();
		Category cat = null;

		// = new SuperCategory();
		if (catDto.getFilter() != null) {
			if (catDto.getFilter().getCatId() != null && catDto.getFilter().getCatId() != 0) {
				cat = catRepository.findByCatId(catDto.getFilter().getCatId());
				if (cat == null) {
					logger.error("Exception occured in getCategoryByFilter() method.");
					throw new KorikeException("Invalid catId");
				}
				catDto.setName(cat.getName());
				catDto.setCatId(cat.getCatId());
				catDto.setSuperCatId(String.valueOf(cat.getSuperCategory().getSuperCatId()));
				catDto.setSuperCatName(cat.getSuperCategory().getName());
				catDtoList.add(catDto);
				// supCatList.add(supCat);
				return catDtoList;
			} else if (catDto.getFilter().getMasterCatId() != null && catDto.getFilter().getMasterCatId() != 0) {
				Optional<SuperCategory> fetchedSupCat = supCatRepository.findById(catDto.getFilter().getMasterCatId());
				if (!fetchedSupCat.isPresent()) {
					logger.error("Exception occured in getCategoryByFilter() method.");
					throw new KorikeException("Invalid supcatId");
				}

				return catRepository.getCatBySupCatId(fetchedSupCat.get().getSuperCatId()).stream()
						.map(spc -> new CategoryDto(spc.getCatId(), spc.getName(),
								String.valueOf(spc.getSuperCategory().getSuperCatId()), spc.getSuperCategory().getName()))
						.collect(Collectors.toList());
			} else {
				return catRepository.findAll().stream()
						.map(spc -> new CategoryDto(spc.getCatId(), spc.getName(),
								String.valueOf(spc.getSuperCategory().getSuperCatId()), spc.getSuperCategory().getName()))
						.collect(Collectors.toList());
			}
		} else {
			logger.error("Exception occured in getSupCatByFilter() method.");
			throw new KorikeException("Invalid SupCatId");
		}
	}

}
