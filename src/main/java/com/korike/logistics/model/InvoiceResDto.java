package com.korike.logistics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.korike.logistics.entity.Items;
import com.korike.logistics.model.items.ItemCostBreakUp;
import com.korike.logistics.model.orders.OrderItemsListDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data @AllArgsConstructor @NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class InvoiceResDto {
	
	@JsonProperty("invoicePath")
	private String invoicePath;

	@JsonProperty("totalItemCost")
	private double totalItemCost;

	@JsonProperty("totalTaxes")
	private double totalTaxes;

	@JsonProperty("deliveryCharges")
	private double deliveryCharges;

	@JsonProperty("discount")
	private double discount;

	@JsonProperty("totalPayable")
	private double totalPayable;

	public double getTotalPayable() {
		return totalPayable;
	}

	public void setTotalPayable(double totalPayable) {
		this.totalPayable = totalPayable;
	}

	@JsonProperty("itemCostBreakup")
	private List<ItemCostBreakUp> itemCostBreakup;
	
//	public List<OrderItemsListDto> itemsDetails;
	public List<Items> itemDetails;

	public List<Items> getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(List<Items> itemDetails) {
		this.itemDetails = itemDetails;
	}

	public String getInvoicePath() {
		return invoicePath;
	}

	public void setInvoicePath(String invoicePath) {
		this.invoicePath = invoicePath;
	}

	public double getTotalItemCost() {
		return totalItemCost;
	}

	public void setTotalItemCost(double totalItemCost) {
		this.totalItemCost = totalItemCost;
	}

	public double getTotalTaxes() {
		return totalTaxes;
	}

	public void setTotalTaxes(double totalTaxes) {
		this.totalTaxes = totalTaxes;
	}

	public double getDeliveryCharges() {
		return deliveryCharges;
	}

	public void setDeliveryCharges(double deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public List<ItemCostBreakUp>  getItemCostBreakup() {
		return itemCostBreakup;
	}

	public void setItemCostBreakup(List<ItemCostBreakUp>  itemCostBreakup) {
		this.itemCostBreakup = itemCostBreakup;
	}
	
}
