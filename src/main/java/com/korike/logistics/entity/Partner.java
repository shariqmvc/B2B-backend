package com.korike.logistics.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@Table(name="partner")
@Data @NoArgsConstructor @AllArgsConstructor
@DynamicUpdate
public class Partner {

	@Id
	@Column(name="partner_id", length=100)
	private String partnerId;
	@Column(name="partner_type", nullable = true)
	private String partnerType;
	@Column(name="partner_reg", nullable = true)
	private String partnerReg;
	@Column(name="partner_contact", nullable = true)
	private String partnerContact;
	@Column(name="partner_files", nullable = true)
	@ElementCollection(targetClass=String.class)
	private List<String> partnerFiles = new ArrayList<>();
	@Column(name="partner_status", nullable = true)
	private String partnerStatus;
	@Column(name="partner_name", nullable = true)
	private String partnerName;
	@Column(name="partner_description", nullable = true)
	private String partnerDescription;
	@Column(name="partner_gst", nullable = true)
	private String partnerGst;
	@Column(name="partner_pan", nullable = true)
	private String partnerPan;

	public String getPartnerLocation() {
		return partnerLocation;
	}

	public void setPartnerLocation(String partnerLocation) {
		this.partnerLocation = partnerLocation;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

	@Column(name="partner_location", nullable = true, columnDefinition="TEXT")
	private String partnerLocation;


	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	private User user;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "service_id", nullable = false)
	private ServiceDefinition serviceDefintion;

	public String getPartnerContact() {
		return partnerContact;
	}

	public void setPartnerContact(String partnerContact) {
		this.partnerContact = partnerContact;
	}

	/*	@ManyToOne(fetch = FetchType.LAZY, cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
        @JoinColumn(name = "promo_id")
        private Promo promo; */
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
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getPartnerType() {
		return partnerType;
	}
	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}
	public List<String> getPartnerFiles() {
		return partnerFiles;
	}
	public void setPartnerFiles(List<String> partnerFiles) {
		this.partnerFiles = partnerFiles;
	}
	public String getPartnerStatus() {
		return partnerStatus;
	}
	public void setPartnerStatus(String partnerStatus) {
		this.partnerStatus = partnerStatus;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getPartnerDescription() {
		return partnerDescription;
	}
	public void setPartnerDescription(String partnerDescription) {
		this.partnerDescription = partnerDescription;
	}
	public String getPartnerGst() {
		return partnerGst;
	}
	public void setPartnerGst(String partnerGst) {
		this.partnerGst = partnerGst;
	}
	public String getPartnerPan() {
		return partnerPan;
	}
	public void setPartnerPan(String partnerPan) {
		this.partnerPan = partnerPan;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ServiceDefinition getServiceDefintion() {
		return serviceDefintion;
	}
	public void setServiceDefintion(ServiceDefinition serviceDefintion) {
		this.serviceDefintion = serviceDefintion;
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

	public String getPartnerReg() {
		return partnerReg;
	}

	public void setPartnerReg(String partnerReg) {
		this.partnerReg = partnerReg;
	}
}
