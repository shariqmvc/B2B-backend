package com.korike.logistics.model.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Filter {
	
	@JsonProperty("s_name")
	private String sName;
	@JsonProperty("s_id")
	private String sId;
	@JsonProperty("s_active")
	private Boolean sActive;
	
	
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public Boolean getsActive() {
		return sActive;
	}
	public void setsActive(Boolean sActive) {
		this.sActive = sActive;
	}
	
	
	
	
}
