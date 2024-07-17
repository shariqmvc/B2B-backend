package com.korike.logistics.model.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.korike.logistics.entity.PaymentTransactionDetails;
import com.korike.logistics.model.payments.PaymentTransactionDetailsDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderReqDto {

	@JsonProperty("o_details")
	private OrderDetails oDetails;
	@JsonProperty("transaction_details")
	private PaymentTransactionDetailsDto transactionDetails;

	public OrderDetails getoDetails() {
		return oDetails;
	}

	public void setoDetails(OrderDetails oDetails) {
		this.oDetails = oDetails;
	}

	public PaymentTransactionDetailsDto getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(PaymentTransactionDetailsDto transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
}
