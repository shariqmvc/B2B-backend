package com.korike.logistics.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name="service_definition")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceDefinition {
	@Id
	@Column(name = "service_id")
	@JsonProperty("service_id")
	private String service_id;
	@Column(name = "service_name")
	@JsonProperty("service_name")
	private String service_name;
	@Column(name = "service_details")
	@JsonProperty("service_details")
	private String service_details;
	@Column(name = "service_description")
	@JsonProperty("service_description")
	private String service_description;
	@Column(name = "service_icon_path")
	@JsonProperty("service_icon_path")
	private String service_icon_path;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp createdAt;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp lastUpdatedAt;
	@Column(name = "created_by", nullable = true)
	private String createdBy;
	@Column(name = "last_updated_by", nullable = true)
	private String lastUpdatedBy;
	@Column(name = "is_active", nullable = true)
	@JsonProperty("is_active")
	private Boolean is_active;
	@Column(name = "is_multiple_allowed", nullable = true)
	@JsonProperty("is_multiple_allowed")
	private Boolean is_multiple_allowed;

	public Boolean getMultipleAllowed() {
		return is_multiple_allowed;
	}

	public void setMultipleAllowed(Boolean multipleAllowed) {
		is_multiple_allowed = multipleAllowed;
	}

	public String getServiceId() {
		return service_id;
	}
	public void setServiceId(String serviceId) {
		this.service_id = serviceId;
	}
	public String getServiceDetails() {
		return service_details;
	}
	public void setServiceDetails(String serviceDetails) {
		this.service_details = serviceDetails;
	}
	public String getServiceIconPath() {
		return service_icon_path;
	}
	public void setServiceIconPath(String serviceIconPath) {
		this.service_icon_path = serviceIconPath;
	}
	
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getLastUpdatedAt() {
		return lastUpdatedAt;
	}
	public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Boolean getIsActive() {
		return is_active;
	}
	public void setIsActive(Boolean isActive) {
		this.is_active = isActive;
	}
	public String getServiceDescription() {
		return service_description;
	}
	public void setServiceDescription(String serviceDescription) {
		this.service_description = serviceDescription;
	}
	public String getServiceName() {
		return service_name;
	}
	public void setServiceName(String serviceName) {
		this.service_name = serviceName;
	}

}
