package com.korike.logistics.model.payments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDetails {

	@JsonProperty("wallet_balance")
	private Double walletBalance;
	@JsonProperty("discount")
	private Double discount;
	@JsonProperty("total")
	private Double total;
	@JsonProperty("taxes")
	private Tax taxes;
	@JsonProperty("amount")
	private Double amount;
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getWalletBalance() {
		return walletBalance;
	}
	public void setWalletBalance(Double walletBalance) {
		this.walletBalance = walletBalance;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Tax getTaxes() {
		return taxes;
	}
	public void setTaxes(Tax taxes) {
		this.taxes = taxes;
	}
	
	
	
}
