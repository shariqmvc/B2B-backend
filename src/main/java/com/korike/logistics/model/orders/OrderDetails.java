package com.korike.logistics.model.orders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.korike.logistics.entity.PaymentTransactionDetails;
import com.korike.logistics.model.InvoiceResDto;
import com.korike.logistics.model.PartnerLocation;
import com.korike.logistics.model.UserInfoDto;
import com.korike.logistics.model.payments.PaymentTransactionDetailsDto;
import org.codehaus.jettison.json.JSONObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetails {
	
	@JsonProperty("s_id")
	private String sid;
	@JsonProperty("qty")
	private Integer qty;
	@JsonProperty("source_location")
	private String sourceLocation;
	@JsonProperty("destination_location")
	private String destinationLocation;
	@JsonProperty("schedule")
	private Schedule schedule;
	@JsonProperty("o_attachment")
	private String attachment;
	@JsonProperty("o_paymode")
	private String paymentMode;
	@JsonProperty("o_instructions")
	private String instruction;
	@JsonProperty("message")
	private String message;
	@JsonProperty("items")
	private List<OrderItemsListDto> items;
	@JsonProperty("order_status")
	private String orderStatus;
	@JsonProperty("invoice")
	private InvoiceResDto invoiceResDto;
	private UserInfoDto userInfo;

	public InvoiceResDto getInvoiceResDto() {
		return invoiceResDto;
	}

	public void setInvoiceResDto(InvoiceResDto invoiceResDto) {
		this.invoiceResDto = invoiceResDto;
	}

	public PartnerLocation getPartnerLocation() {
		return partnerLocation;
	}

	public void setPartnerLocation(PartnerLocation partnerLocation) {
		this.partnerLocation = partnerLocation;
	}

	@JsonProperty("partner_location")
	private PartnerLocation partnerLocation;

	
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public String getSourceLocation() {
		return sourceLocation;
	}
	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	public String getDestinationLocation() {
		return destinationLocation;
	}
	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public List<OrderItemsListDto> getItems() {
		return items;
	}
	public void setItems(List<OrderItemsListDto> items) {
		this.items = items;
	}


	public UserInfoDto getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoDto userInfo) {
		this.userInfo = userInfo;
	}

	
}
