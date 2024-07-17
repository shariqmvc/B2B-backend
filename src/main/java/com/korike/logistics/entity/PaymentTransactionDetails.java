package com.korike.logistics.entity;

import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="payment_transaction_details")
@Data @NoArgsConstructor @AllArgsConstructor
public class PaymentTransactionDetails {

	@Id
	@Column(name="pay_trans_id", length=100, nullable = false)
	private String payTransId;
	
	@Column(name="payment_type")
	private String paymentType;
	
	@Column(name="status")
	private String status;
	
	@Column(name="payment_details", columnDefinition="TEXT")
	private String paymentDetails;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp createdAt;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp lastUpdatedAt;
	@Column(name = "created_by", nullable = true)
	private String createdBy;
	@Column(name = "last_updated_by", nullable = true)
	private String lastUpdatedBy;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id", nullable = false)
	private CustomerOrderDetails customerOrderDetails;

	public CustomerOrderDetails getCustomerOrderDetails() {
		return customerOrderDetails;
	}

	public void setCustomerOrderDetails(CustomerOrderDetails customerOrderDetails) {
		this.customerOrderDetails = customerOrderDetails;
	}

	public String getPayTransId() {
		return payTransId;
	}

	public void setPayTransId(String payTransId) {
		this.payTransId = payTransId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(String paymentDetails) {
		this.paymentDetails = paymentDetails;
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
