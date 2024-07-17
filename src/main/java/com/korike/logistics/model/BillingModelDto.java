package com.korike.logistics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class BillingModelDto {

	@JsonProperty("billing_id")
	private Long billingId;
	@JsonProperty("unit")
	private String unit;
	@JsonProperty("type")
	private String type;
	@JsonProperty("promo_id")
	private Long promoId;
	@JsonProperty("commission_id")
	private Long commissionId;
	@JsonProperty("partner_id")
	private String partnerId;
	@JsonProperty("wholeSale_limits")
	private WholeSaleLimits wholeSaleLimits;
	@JsonProperty("is_active")
	private Boolean isActive;
	@JsonProperty("filters")
	private BillingFilter filters;

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

	public BillingFilter getFilters() {
		return filters;
	}

	public void setFilters(BillingFilter filters) {
		this.filters = filters;
	}

	public Long getBillingId() {
		return billingId;
	}
	public void setBillingId(Long billingId) {
		this.billingId = billingId;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getPromoId() {
		return promoId;
	}
	public void setPromoId(Long promoId) {
		this.promoId = promoId;
	}
	public Long getCommissionId() {
		return commissionId;
	}
	public void setCommissionId(Long commissionId) {
		this.commissionId = commissionId;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public WholeSaleLimits getWholeSaleLimits() {
		return wholeSaleLimits;
	}
	public void setWholeSaleLimits(WholeSaleLimits wholeSaleLimits) {
		this.wholeSaleLimits = wholeSaleLimits;
	}
	
	
	
	
	
}
