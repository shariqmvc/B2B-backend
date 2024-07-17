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
@Table(name="invoice")
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Invoice {

	@Id
	@Column(name="invoice_id", length=100)
	private String invoiceId;
	
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "order_id", nullable = true)
	private CustomerOrderDetails customerOrdDetails;
	
	
	@Column(name="invoice_details", nullable = true, columnDefinition="TEXT")
	private String invoiceDetails;

	@Column(name="payment_type")
	private String paymentType;
	
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
	
	
	
	public Invoice(String invoiceId, CustomerOrderDetails customerOrdDetails, String invoiceDetails, String paymentType,
			ServiceDefinition service, Timestamp createdAt, Timestamp lastUpdatedAt, String createdBy,
			String lastUpdatedBy) {
		super();
		this.invoiceId = invoiceId;
		this.customerOrdDetails = customerOrdDetails;
		this.invoiceDetails = invoiceDetails;
		this.paymentType = paymentType;
		this.service = service;
		this.createdAt = createdAt;
		this.lastUpdatedAt = lastUpdatedAt;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public Invoice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public CustomerOrderDetails getCustomerOrdDetails() {
		return customerOrdDetails;
	}
	public void setCustomerOrdDetails(CustomerOrderDetails customerOrdDetails) {
		this.customerOrdDetails = customerOrdDetails;
	}
	public String getInvoiceDetails() {
		return invoiceDetails;
	}
	public void setInvoiceDetails(String invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
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
