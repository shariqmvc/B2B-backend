package com.korike.logistics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CategoryDto {

	@JsonProperty("cat_id")
	private Long catId;
	@JsonProperty("name")
	private String name;
	@JsonProperty("super_cat_id")
	private String superCatId;
	@JsonProperty("super_cat_name")
	private String superCatName;
	@JsonProperty("filter")
	private CatFilter filter;
	

	public CategoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CategoryDto(Long catId, String name, String superCatId, String superCatName) {
		super();
		this.catId = catId;
		this.name = name;
		this.superCatId = superCatId;
		this.superCatName = superCatName;
	}
	public Long getCatId() {
		return catId;
	}
	public void setCatId(Long catId) {
		this.catId = catId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSuperCatId() {
		return superCatId;
	}
	public void setSuperCatId(String superCatId) {
		this.superCatId = superCatId;
	}
	public String getSuperCatName() {
		return superCatName;
	}
	public void setSuperCatName(String superCatName) {
		this.superCatName = superCatName;
	}
	public CatFilter getFilter() {
		return filter;
	}
	public void setFilter(CatFilter filter) {
		this.filter = filter;
	}
	
	
}
