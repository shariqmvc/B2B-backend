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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="customer_bill")
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CustomerBill {

	@Id
	@Column(name="customer_bill_id", length=100)
	private String customerBillId;
	
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "order_id", nullable = true)
	private CustomerOrderDetails customerOrderDetail;
	
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "invoice_id", nullable = true)
	private Invoice invoice;
	
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "service_id", nullable = true)
	private ServiceDefinition service;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp createdAt;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp lastUpdatedAt;
	@Column(name = "created_by", nullable = true)
	private String createdBy;
	@Column(name = "last_updated_by", nullable = true)
	private String lastUpdatedBy;
	
	
	
	public CustomerBill() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomerBill(String customerBillId, CustomerOrderDetails customerOrderDetail, Invoice invoice,
			ServiceDefinition service, Timestamp createdAt, Timestamp lastUpdatedAt, String createdBy,
			String lastUpdatedBy) {
		super();
		this.customerBillId = customerBillId;
		this.customerOrderDetail = customerOrderDetail;
		this.invoice = invoice;
		this.service = service;
		this.createdAt = createdAt;
		this.lastUpdatedAt = lastUpdatedAt;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getCustomerBillId() {
		return customerBillId;
	}
	public void setCustomerBillId(String customerBillId) {
		this.customerBillId = customerBillId;
	}
	public CustomerOrderDetails getCustomerOrderDetail() {
		return customerOrderDetail;
	}
	public void setCustomerOrderDetail(CustomerOrderDetails customerOrderDetail) {
		this.customerOrderDetail = customerOrderDetail;
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	public ServiceDefinition getService() {
		return service;
	}
	public void setService(ServiceDefinition service) {
		this.service = service;
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
	
	
}
