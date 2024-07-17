package com.korike.logistics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ItemPackageCategoryFilter {
	
	@JsonProperty("item_pack_cat_id")
	private Long itemPackageCategoryId;

	public Long getItemPackageCategoryId() {
		return itemPackageCategoryId;
	}

	public void setItemPackageCategoryId(Long itemPackageCategoryId) {
		this.itemPackageCategoryId = itemPackageCategoryId;
	}
	
	
}
