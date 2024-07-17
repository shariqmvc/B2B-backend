package com.korike.logistics.model.wallet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class WalletAddDto {

	private String walletId;
	private String walletBalance;
//	private String walletCurrencyBalance;
	private String totalGained;
	private String totalRedeemed;
	private String walletStatus;
	public String getWalletId() {
		return walletId;
	}
	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}
	public String getWalletBalance() {
		return walletBalance;
	}
	public void setWalletBalance(String walletBalance) {
		this.walletBalance = walletBalance;
	}
	public String getTotalGained() {
		return totalGained;
	}
	public void setTotalGained(String totalGained) {
		this.totalGained = totalGained;
	}
	public String getTotalRedeemed() {
		return totalRedeemed;
	}
	public void setTotalRedeemed(String totalRedeemed) {
		this.totalRedeemed = totalRedeemed;
	}
	public String getWalletStatus() {
		return walletStatus;
	}
	public void setWalletStatus(String walletStatus) {
		this.walletStatus = walletStatus;
	}
	
	
}
