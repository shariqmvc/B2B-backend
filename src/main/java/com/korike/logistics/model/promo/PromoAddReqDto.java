package com.korike.logistics.model.promo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.korike.logistics.model.promo.Filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data @AllArgsConstructor @NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PromoAddReqDto {

	@JsonProperty("promo_id")
	private Long promoId;
	@JsonProperty("promo_code")
	private String promoCode;
	@JsonProperty("promo_percent")
	private Double promoPercent;
	@JsonProperty("partner_id")
	private String partnerId;
	@JsonProperty("service_id")
	private String serviceId;
	@JsonProperty("min_bill")
	private Double minBill;
	@JsonProperty("max_discount")
	private Double maxDiscount;
	@JsonProperty("max_cashback")
	private Double maxCashback;
	@JsonProperty("expiry")
	private Long expiry;
	@JsonProperty("isActive")
	private Boolean isActive;
	@JsonProperty("onWalletCreate")
	private Boolean onWalletCreate;
	@JsonProperty("addCashOnWalletCreate")
	private Boolean addCashOnWalletCreate;
	@JsonProperty("filters")
	private Filter filter;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public PromoAddReqDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PromoAddReqDto(Long promoId, String promoCode, Double promoPercent, String partnerId, Double minBill,
			Double maxDiscount, Double maxCashback, Long expiry, Boolean isActive, Boolean onWalletCreate,
			Boolean addCashOnWalletCreate, Filter filter) {
		super();
		this.promoId = promoId;
		this.promoCode = promoCode;
		this.promoPercent = promoPercent;
		this.partnerId = partnerId;
		this.minBill = minBill;
		this.maxDiscount = maxDiscount;
		this.maxCashback = maxCashback;
		this.expiry = expiry;
		this.isActive = isActive;
		this.onWalletCreate = onWalletCreate;
		this.addCashOnWalletCreate = addCashOnWalletCreate;
		this.filter = filter;
	}
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
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
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
	public Filter getFilter() {
		return filter;
	}
	public void setFilter(Filter filter) {
		this.filter = filter;
	}
	
	
}
