package com.korike.logistics.service;

import java.util.List;

import com.korike.logistics.entity.ItemPackageCategory;
import com.korike.logistics.model.ItemPackageCategoryDto;

public interface ItemPackCatService {

	public ItemPackageCategoryDto createItemPackCat(ItemPackageCategoryDto itemPackCatDto);
	public ItemPackageCategoryDto updateItemPackCat(ItemPackageCategoryDto itemPackCatDto, ItemPackageCategory fetchedItemPackCat);
	public List<ItemPackageCategoryDto> getItemPackCatByFilter(ItemPackageCategoryDto itemPackCatDto);
	
}
