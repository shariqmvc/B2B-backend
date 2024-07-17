package com.korike.logistics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CatFilter {
	
	
	@JsonProperty("id")
	private Long catId;
	@JsonProperty("master_cat_id")
	private Long masterCatId;

	public Long getCatId() {
		return catId;
	}

	public void setCatId(Long catId) {
		this.catId = catId;
	}

	public Long getMasterCatId() {
		return masterCatId;
	}

	public void setMasterCatId(Long masterCatId) {
		this.masterCatId = masterCatId;
	}
	
	
}
