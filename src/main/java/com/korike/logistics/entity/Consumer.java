package com.korike.logistics.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.korike.logistics.model.orders.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="consumer")
//@Data @NoArgsConstructor @AllArgsConstructor
public class Consumer {

	@Id
	@Column(name="consumer_id", length=100)
	private String consumerId;
	@Column(name="consumer_type", nullable = true)
	private String consumerType;
	@Column(name="consumer_details", nullable = true, columnDefinition="TEXT")
	private String consumerDetails;
	@Column(name="consumer_files", nullable = true)
	private Byte[] consumerFiles;
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	private User user;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp createdAt;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp lastUpdatedAt;
	@Column(name = "created_by", nullable = true)
	private String createdBy;
	@Column(name = "last_updated_by", nullable = true)
	private String lastUpdatedBy;
	@Column(name = "is_active", nullable = true)
	private Boolean isActive;

	@Column(name = "consumer_status", nullable = true)
	private String consumerStatus;
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "order_id", nullable = true)
	private CustomerOrderDetails orderDetails;


	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	public String getConsumerType() {
		return consumerType;
	}
	public void setConsumerType(String consumerType) {
		this.consumerType = consumerType;
	}
	public String getConsumerDetails() {
		return consumerDetails;
	}
	public void setConsumerDetails(String consumerDetails) {
		this.consumerDetails = consumerDetails;
	}
	public Byte[] getConsumerFiles() {
		return consumerFiles;
	}
	public void setConsumerFiles(Byte[] consumerFiles) {
		this.consumerFiles = consumerFiles;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getConsumerStatus() {
		return consumerStatus;
	}
	public void setConsumerStatus(String consumerStatus) {
		this.consumerStatus = consumerStatus;
	}
	public CustomerOrderDetails getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(CustomerOrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	

}
