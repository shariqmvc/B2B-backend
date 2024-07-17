package com.korike.logistics.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.korike.logistics.entity.SuperCategory;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.model.SuperCategoryDto;
import com.korike.logistics.repository.SuperCategoryRepository;
import com.korike.logistics.service.SupCatService;
import com.korike.logistics.util.CommonUtils;

@Service
public class SupCatServiceImpl implements SupCatService {

	private static Logger logger = Logger.getLogger(SupCatServiceImpl.class);
	
	@Autowired
	SuperCategoryRepository supCatRepository;

	@Override
	public SuperCategoryDto createSupCat(SuperCategoryDto supCat) {
		logger.info("Started createSupCat() method ");
		
		SuperCategory newSupCat = new SuperCategory();
		newSupCat.setName(supCat.getName());
		newSupCat.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		newSupCat.setCreatedBy(CommonUtils.getCurrentUserName());
		newSupCat.setIsActive(true);
		
		try {
			newSupCat = supCatRepository.save(newSupCat);
			supCat.setSuperCatId(newSupCat.getSuperCatId());
		}catch(Exception exc) {
			logger.error("Exception occured in createSupCat() method.");
			throw new KorikeException("Internal Server Error");
		}
		
		return supCat;
	}

	@Override
	public SuperCategoryDto updateSupCat(SuperCategoryDto supCat, SuperCategory fetchedSupCat) {
		logger.info("Started updateSupCat() method ");
		
		fetchedSupCat.setName(supCat.getName());
		fetchedSupCat.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
		fetchedSupCat.setLastUpdatedBy(CommonUtils.getCurrentUserName());
		//fetchedSupCat.setIsActive(true);
		
		try {
			fetchedSupCat = supCatRepository.save(fetchedSupCat);
			//supCat.setSuperCatId(newSupCat.getSuperCatId());
		}catch(Exception exc) {
			logger.error("Exception occured in updateSupCat() method.");
			throw new KorikeException("Internal Server Error");
		}
		
		return supCat;
	}

	@Override
	public List<SuperCategoryDto> getSupCatByFilter(SuperCategoryDto supCatdto) {
		List<SuperCategory> supCatList = new ArrayList();
		List<SuperCategoryDto> supCatDtoList = new ArrayList();
		SuperCategory supCat = null;
		
		//= new SuperCategory();
		if(supCatdto.getFilter() != null) {
			if(supCatdto.getFilter().getCatId() != null && supCatdto.getFilter().getCatId() != 0) {
				supCat = supCatRepository.findBySuperCatId(supCatdto.getFilter().getCatId());
				if(supCat == null) {
					logger.error("Exception occured in getSupCatByFilter() method.");
					throw new KorikeException("Invalid SupCatId");
				}
				supCatdto.setFilter(null);
				supCatdto.setName(supCat.getName());
				supCatdto.setSuperCatId(supCat.getSuperCatId());
				supCatDtoList.add(supCatdto);
				//supCatList.add(supCat);
				return supCatDtoList;
			}else {
			return 	supCatRepository.findAll().stream()
				.map(spc -> new SuperCategoryDto(spc.getSuperCatId(),spc.getName()))
				.collect(Collectors.toList());
			}
		}else {
			logger.error("Exception occured in getSupCatByFilter() method.");
			throw new KorikeException("Invalid SupCatId");
		}
		
	}

}
