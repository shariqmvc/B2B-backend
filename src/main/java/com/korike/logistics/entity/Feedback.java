package com.korike.logistics.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="feedback")
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feed_back_id")
	private Long feedbackId;
	@Column(name = "points")
	private Long points;
	@Column(name = "message")
	private String message;
	@Column(name = "tips")
	private String tips;
	
/*	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "consumer_id", nullable = false)
	private Consumer consumer;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "partner_id", nullable = false)
	private ServicePartner servicePartner; */
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id", nullable = false)
	private CustomerOrderDetails orderDetail;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp createdAt;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp lastUpdatedAt;
	@Column(name = "created_by", nullable = true)
	private String createdBy;
	@Column(name = "last_updated_by", nullable = true)
	private String lastUpdatedBy;
	
	
	public Long getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(Long feedbackId) {
		this.feedbackId = feedbackId;
	}
	public Long getPoints() {
		return points;
	}
	public void setPoints(Long points) {
		this.points = points;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	
/*	public Consumer getConsumer() {
		return consumer;
	}
	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}
	public ServicePartner getServicePartner() {
		return servicePartner;
	}
	public void setServicePartner(ServicePartner servicePartner) {
		this.servicePartner = servicePartner;
	} */
	
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
	public CustomerOrderDetails getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(CustomerOrderDetails orderDetail) {
		this.orderDetail = orderDetail;
	}
	
	
}
