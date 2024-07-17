package com.korike.logistics.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.korike.logistics.model.partner.PartnerDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="promo")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Promo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="promo_id")
	private Long promoId;
	@Column(name="promo_code", nullable = true)
	private String promoCode;
	@Column(name="promo_percent", nullable = true)
	private Double promoPercent;
	@Column(name="promo_cash", nullable = true)
	private Double promoCash;
	
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "partner_id", nullable = true)
	private Partner partner;

	public ServiceDefinition getServiceDefinition() {
		return serviceDefinition;
	}

	public void setServiceDefinition(ServiceDefinition serviceDefinition) {
		this.serviceDefinition = serviceDefinition;
	}

	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "service_id", nullable = true)
	private ServiceDefinition serviceDefinition;

	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "cat_id", nullable = true)
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public SuperCategory getSuperCategory() {
		return superCategory;
	}

	public void setSuperCategory(SuperCategory superCategory) {
		this.superCategory = superCategory;
	}

	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "sub_cat_id", nullable = true)
	private SubCategory subCategory;

	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "super_cat_id", nullable = true)
	private SuperCategory superCategory;
	
	@Column(name="min_bill", nullable = true)
	private Double minBill;
	@Column(name="max_discount", nullable = true)
	private Double maxDiscount;
	@Column(name="max_cashback", nullable = true)
	private Double maxCashback;
	@Column(name="min_quantity", nullable = true)
	private Double minQuantity;

	public Double getMinWeight() {
		return minWeight;
	}

	public void setMinWeight(Double minWeight) {
		this.minWeight = minWeight;
	}

	@Column(name="min_weight", nullable = true)
	private Double minWeight;

	public Double getMinQuantity() {
		return minQuantity;
	}

	public void setMinQuantity(Double minQuantity) {
		this.minQuantity = minQuantity;
	}

	public String getMinSize() {
		return minSize;
	}

	public void setMinSize(String minSize) {
		this.minSize = minSize;
	}

	public Double getMinTime() {
		return minTime;
	}

	public void setMinTime(Double minTime) {
		this.minTime = minTime;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

	@Column(name="min_size", nullable = true)
	private String minSize;
	@Column(name="min_time", nullable = true)
	private Double minTime;
	@Column(name="expiry", nullable = true)
	private Long expiry;
	@Column(name="isActive", nullable = true)
	private Boolean isActive;
	@Column(name="onWalletCreate", nullable = true)
	private Boolean onWalletCreate;
	@Column(name="addCashOnWalletCreate", nullable = true)
	private Boolean addCashOnWalletCreate;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp createdAt;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp lastUpdatedAt;
	@Column(name = "created_by", nullable = true)
	private String createdBy;
	@Column(name = "last_updated_by", nullable = true)
	private String lastUpdatedBy;
	public Long getPromoId() {
		return promoId;
	}
	public void setPromoId(Long promoId) {
		this.promoId = promoId;
	}
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public Double getPromoPercent() {
		return promoPercent;
	}
	public void setPromoPercent(Double promoPercent) {
		this.promoPercent = promoPercent;
	}
	public Double getPromoCash() {
		return promoCash;
	}
	public void setPromoCash(Double promoCash) {
		this.promoCash = promoCash;
	}
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	public Double getMinBill() {
		return minBill;
	}
	public void setMinBill(Double minBill) {
		this.minBill = minBill;
	}
	public Double getMaxDiscount() {
		return maxDiscount;
	}
	public void setMaxDiscount(Double maxDiscount) {
		this.maxDiscount = maxDiscount;
	}
	public Double getMaxCashback() {
		return maxCashback;
	}
	public void setMaxCashback(Double maxCashback) {
		this.maxCashback = maxCashback;
	}
	public Long getExpiry() {
		return expiry;
	}
	public void setExpiry(Long expiry) {
		this.expiry = expiry;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getOnWalletCreate() {
		return onWalletCreate;
	}
	public void setOnWalletCreate(Boolean onWalletCreate) {
		this.onWalletCreate = onWalletCreate;
	}
	public Boolean getAddCashOnWalletCreate() {
		return addCashOnWalletCreate;
	}
	public void setAddCashOnWalletCreate(Boolean addCashOnWalletCreate) {
		this.addCashOnWalletCreate = addCashOnWalletCreate;
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
