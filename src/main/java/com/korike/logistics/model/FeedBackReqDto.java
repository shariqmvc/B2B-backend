package com.korike.logistics.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.korike.logistics.entity.CustomerOrderDetails;
import com.korike.logistics.model.orders.OrderReqDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data @AllArgsConstructor @NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class FeedBackReqDto {
	
	@JsonProperty("star")
	private Integer star;
	@JsonProperty("feedbackText")
	private String feedBackText;

	public FeedBackReqDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FeedBackReqDto(Integer star, String feedBackText) {
		super();
		this.star = star;
		this.feedBackText = feedBackText;
	}
	public Integer getStar() {
		return star;
	}
	public void setStar(Integer star) {
		this.star = star;
	}
	public String getFeedBackText() {
		return feedBackText;
	}
	public void setFeedBackText(String feedBackText) {
		this.feedBackText = feedBackText;
	}
	
	
}
