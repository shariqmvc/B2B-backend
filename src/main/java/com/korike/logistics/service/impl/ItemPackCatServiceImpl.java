package com.korike.logistics.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.korike.logistics.entity.ItemPackageCategory;
import com.korike.logistics.entity.SuperCategory;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.model.ItemPackageCategoryDto;
import com.korike.logistics.model.SuperCategoryDto;
import com.korike.logistics.repository.ItemPackageCategoryRepository;
import com.korike.logistics.service.ItemPackCatService;
import com.korike.logistics.util.CommonUtils;

@Service
public class ItemPackCatServiceImpl implements ItemPackCatService {

	private static Logger logger = Logger.getLogger(ItemPackCatServiceImpl.class);
	@Autowired
	ItemPackageCategoryRepository itemPackCatRepository;

	@Override
	public ItemPackageCategoryDto createItemPackCat(ItemPackageCategoryDto itemPackCatDto) {

		ItemPackageCategory newItemPackCat = new ItemPackageCategory();
		newItemPackCat.setName(itemPackCatDto.getName());
		newItemPackCat.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		newItemPackCat.setCreatedBy(CommonUtils.getCurrentUserName());
		newItemPackCat.setIsActive(true);

		try {
			newItemPackCat = itemPackCatRepository.save(newItemPackCat);
			itemPackCatDto.setItemPackageCategoryId(newItemPackCat.getItemPackageCategoryId());
		} catch (Exception exc) {
			logger.error("Exception occured in createItemPackCat() method.");
			throw new KorikeException("Internal Server Error");
		}
		return itemPackCatDto;
	}

	@Override
	public ItemPackageCategoryDto updateItemPackCat(ItemPackageCategoryDto itemPackCatDto,
			ItemPackageCategory fetchedItemPackCat) {

		fetchedItemPackCat.setName(itemPackCatDto.getName());
		fetchedItemPackCat.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
		fetchedItemPackCat.setLastUpdatedBy(CommonUtils.getCurrentUserName());

		try {
			fetchedItemPackCat = itemPackCatRepository.save(fetchedItemPackCat);
			itemPackCatDto.setItemPackageCategoryId(fetchedItemPackCat.getItemPackageCategoryId());
		} catch (Exception exc) {
			logger.error("Exception occured in updateItemPackCat() method.");
			throw new KorikeException("Internal Server Error");
		}
		return itemPackCatDto;
	}

	@Override
	public List<ItemPackageCategoryDto> getItemPackCatByFilter(ItemPackageCategoryDto itemPackCatDto) {

		List<ItemPackageCategory> itemPackCatList = new ArrayList();
		List<ItemPackageCategoryDto> itemPackCatDtoList = new ArrayList();
		ItemPackageCategory ItemPackCat = null;
		
		//= new SuperCategory();
		if(itemPackCatDto.getFilter() != null) {
			if(itemPackCatDto.getFilter().getItemPackageCategoryId() != null && itemPackCatDto.getFilter().getItemPackageCategoryId() != 0) {
				ItemPackCat = itemPackCatRepository.findByItemPackageCategoryId(itemPackCatDto.getFilter().getItemPackageCategoryId());
				if(ItemPackCat == null) {
					logger.error("Exception occured in getItemPackCatByFilter() method.");
					throw new KorikeException("Invalid ItemPackCatId");
				}
				itemPackCatDto.setFilter(null);
				itemPackCatDto.setName(ItemPackCat.getName());
				itemPackCatDto.setItemPackageCategoryId(ItemPackCat.getItemPackageCategoryId());
				itemPackCatDtoList.add(itemPackCatDto);
				//supCatList.add(supCat);
				return itemPackCatDtoList;
			}else {
			return 	itemPackCatRepository.findAll().stream()
				.map(spc -> new ItemPackageCategoryDto(spc.getItemPackageCategoryId(),spc.getName()))
				.collect(Collectors.toList());
			}
		}else {
			logger.error("Exception occured in getItemPackCatByFilter() method.");
			throw new KorikeException("Internal Server Error");
		}
	}

}
