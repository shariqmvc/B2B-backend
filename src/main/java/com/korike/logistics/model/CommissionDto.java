package com.korike.logistics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CommissionDto {

	@JsonProperty("comm_id")
	private Long commId;
	@JsonProperty("comm_percent")
	private Double commissionPercent;
	@JsonProperty("partner_id")
	private String partnerId;
	@JsonProperty("filter")
	private CommFilter commFilter;
	
	
	
	public CommissionDto(Long commId, Double commissionPercent, String partnerId) {
		super();
		this.commId = commId;
		this.commissionPercent = commissionPercent;
		this.partnerId = partnerId;
	}
	public Long getCommId() {
		return commId;
	}
	public void setCommId(Long commId) {
		this.commId = commId;
	}
	public Double getCommissionPercent() {
		return commissionPercent;
	}
	public void setCommissionPercent(Double commissionPercent) {
		this.commissionPercent = commissionPercent;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public CommFilter getCommFilter() {
		return commFilter;
	}
	public void setCommFilter(CommFilter commFilter) {
		this.commFilter = commFilter;
	}
	
	
	
}
