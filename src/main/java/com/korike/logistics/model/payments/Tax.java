package com.korike.logistics.model.payments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tax {
	
	@JsonProperty("gst1")
	private Double gst1;
	@JsonProperty("gst2")
	private Double gst2;
	
	public Double getGst1() {
		return gst1;
	}
	public void setGst1(Double gst1) {
		this.gst1 = gst1;
	}
	public Double getGst2() {
		return gst2;
	}
	public void setGst2(Double gst2) {
		this.gst2 = gst2;
	}
	
	
}
