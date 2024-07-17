package com.korike.logistics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ItemPackageCategoryDto {
	
	@JsonProperty("item_pack_cat_id")
	private Long itemPackageCategoryId;
	@JsonProperty("name")
	private String name;
	@JsonProperty("filter")
	private ItemPackageCategoryFilter filter;
	
	
	
	public ItemPackageCategoryDto() {
		super();
		
	}
	public ItemPackageCategoryDto(Long itemPackageCategoryId, String name) {
		super();
		this.itemPackageCategoryId = itemPackageCategoryId;
		this.name = name;
	}
	public Long getItemPackageCategoryId() {
		return itemPackageCategoryId;
	}
	public void setItemPackageCategoryId(Long itemPackageCategoryId) {
		this.itemPackageCategoryId = itemPackageCategoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ItemPackageCategoryFilter getFilter() {
		return filter;
	}
	public void setFilter(ItemPackageCategoryFilter filter) {
		this.filter = filter;
	}
	
	
}
