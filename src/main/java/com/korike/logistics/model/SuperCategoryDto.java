package com.korike.logistics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.korike.logistics.entity.SuperCategory;

@JsonInclude(Include.NON_NULL)
public class SuperCategoryDto {

	@JsonProperty("super_cat_id")
	private Long superCatId;
	@JsonProperty("name")
	private String name;
	@JsonProperty("filter")
	private CatFilter filter;
	
	
	
	public SuperCategoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SuperCategoryDto(Long superCatId, String name) {
		super();
		this.superCatId = superCatId;
		this.name = name;
	}
	public Long getSuperCatId() {
		return superCatId;
	}
	public void setSuperCatId(Long superCatId) {
		this.superCatId = superCatId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CatFilter getFilter() {
		return filter;
	}
	public void setFilter(CatFilter filter) {
		this.filter = filter;
	}
	
	
}
