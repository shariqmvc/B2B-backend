package com.korike.logistics.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.korike.logistics.model.orders.OrderDetails;
import com.korike.logistics.model.partner.PartnerDetails;

@Entity
@Table(name="customer_order_details")
public class CustomerOrderDetails {

	@Id
	@Column(name="order_id", length = 100)
	private String orderId;
	@Column(name="location", nullable = true)
	private String Location;
	@Column(name="order_details", nullable = true, columnDefinition="TEXT")
	private String orderDetails;
	@Column(name="order_status", nullable = true)
	private String orderStatus;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp createdAt;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp lastUpdatedAt;
	@Column(name = "created_by", nullable = true)
	private String createdBy;
	@Column(name = "last_updated_by", nullable = true)
	private String lastUpdatedBy;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "consumer_id")
	private Consumer conusmer;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "partner_id")
	private Partner partner;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "service_id", nullable = false)
	private ServiceDefinition serviceDefintion;

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public String getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(String orderDetails) {
		this.orderDetails = orderDetails;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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
	public Consumer getConusmer() {
		return conusmer;
	}
	public void setConusmer(Consumer conusmer) {
		this.conusmer = conusmer;
	}
	public ServiceDefinition getServiceDefintion() {
		return serviceDefintion;
	}
	public void setServiceDefintion(ServiceDefinition serviceDefintion) {
		this.serviceDefintion = serviceDefintion;
	}
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	
	
	
	
	
}
