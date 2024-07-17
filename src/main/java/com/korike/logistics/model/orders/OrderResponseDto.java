package com.korike.logistics.model.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseDto {

	@JsonProperty("orderId")
	private String orderId;
	@JsonProperty("o_description")
	private String orderDesc;
	@JsonProperty("o_details")
	private OrderDetails orderDetails;
	@JsonProperty("o_status")
	private String orderStatus;
	@JsonProperty("eta")
	private String eta;
	@JsonProperty("partner_id")
	private String partnerId;
	@JsonProperty("consumer_id")
	private String consumerId;

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}


	
	
	public OrderResponseDto() {
		super();
		
	}
	public OrderResponseDto(String orderId, String orderDesc, OrderDetails orderDetails, String orderStatus) {
		super();
		this.orderId = orderId;
		this.orderDesc = orderDesc;
		this.orderDetails = orderDetails;
		this.orderStatus = orderStatus;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	public OrderDetails getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getEta() {
		return eta;
	}
	public void setEta(String eta) {
		this.eta = eta;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	
	
	
}
