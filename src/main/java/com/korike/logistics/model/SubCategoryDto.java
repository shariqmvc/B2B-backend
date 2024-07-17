package com.korike.logistics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SubCategoryDto {

	@JsonProperty("sub_cat_id")
	private Long subCatId;
	@JsonProperty("name")
	private String name;
	@JsonProperty("cat_id")
	private String catId;
	@JsonProperty("cat_name")
	private String catName;
	@JsonProperty("filter")
	private CatFilter filter;
	
	
	
	public SubCategoryDto() {
		super();
		
	}
	public SubCategoryDto(Long subCatId, String name, String catId, String catName) {
		super();
		this.subCatId = subCatId;
		this.name = name;
		this.catId = catId;
		this.catName = catName;
	}
	public Long getSubCatId() {
		return subCatId;
	}
	public void setSubCatId(Long subCatId) {
		this.subCatId = subCatId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public CatFilter getFilter() {
		return filter;
	}
	public void setFilter(CatFilter filter) {
		this.filter = filter;
	}
	
	
}
