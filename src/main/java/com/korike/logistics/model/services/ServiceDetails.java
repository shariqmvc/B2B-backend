
package com.korike.logistics.model.services;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceDetails {

	
	@JsonProperty("s_id")
	private String serviceId;
	@JsonProperty("s_name")
	private String name;
	@JsonProperty("s_description")
	private String description;
	@JsonProperty("details")
	private Details details;
	@JsonProperty("filters")
	private Filter filter;
	@JsonProperty("is_active")
	private Boolean isActive;
	@JsonProperty("is_multiple_allowed")
	private Boolean isMultipleAllowed;

	public Boolean getMultipleAllowed() {
		return isMultipleAllowed;
	}

	public void setMultipleAllowed(Boolean multipleAllowed) {
		isMultipleAllowed = multipleAllowed;
	}

	public ServiceDetails(String serviceId, String name, String description, Details details, Boolean isMultipleAllowed) {
		super();
		this.serviceId = serviceId;
		this.name = name;
		this.description = description;
		this.details = details;
		this.isMultipleAllowed = isMultipleAllowed;
	}

	public ServiceDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}
}
