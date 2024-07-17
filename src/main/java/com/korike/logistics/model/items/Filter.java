package com.korike.logistics.model.items;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Filter {

	 @JsonProperty("item_id")
	    private String item_id;

	    @JsonProperty("item_title")
	    private String item_title;

	    @JsonProperty("item_description")
	    private String item_description;
	    
	    @JsonProperty("type")
	    private String type;

	    @JsonProperty("origin")
	    private String origin;
	    
	    @JsonProperty("item_super_category")
	    private String superCategory;

	    @JsonProperty("item_category")
	    private String category;

	    @JsonProperty("item_sub_category")
	    private String subCategory;
	    
	    @JsonProperty("item_pack_cat")
	    private String itemPackCat;

	    @JsonProperty("item_base_quantity")
	    private Double item_base_quantity;

	    @JsonProperty("partner_id")
	    private String partnerId;

	    @JsonProperty("service_id")
	    private String serviceId;

	    @JsonProperty("item_unit")
	    private String itemUnit;

	    @JsonProperty("item_base_cost")
	    private Double itemBaseCost;

	    @JsonProperty("item_min_order")
	    private Double itemMinOrder;

	    @JsonProperty("item_max_order")
	    private Double itemMaxCost;

	    @JsonProperty("item_cost")
	    private Double itemCost;

	    @JsonProperty("is_pre_order")
	    private Boolean isPreOrder;

	    @JsonProperty("is_quotation")
	    private Boolean isQuotation;

	    @JsonProperty("is_active")
	    private Boolean isActive;

		public String getItem_id() {
			return item_id;
		}

		public void setItem_id(String item_id) {
			this.item_id = item_id;
		}

		public String getItem_title() {
			return item_title;
		}

		public void setItem_title(String item_title) {
			this.item_title = item_title;
		}

		public String getItem_description() {
			return item_description;
		}

		public void setItem_description(String item_description) {
			this.item_description = item_description;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getOrigin() {
			return origin;
		}

		public void setOrigin(String origin) {
			this.origin = origin;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getSubCategory() {
			return subCategory;
		}

		public void setSubCategory(String subCategory) {
			this.subCategory = subCategory;
		}

		public Double getItem_base_quantity() {
			return item_base_quantity;
		}

		public void setItem_base_quantity(Double item_base_quantity) {
			this.item_base_quantity = item_base_quantity;
		}

		public String getPartnerId() {
			return partnerId;
		}

		public void setPartnerId(String partnerId) {
			this.partnerId = partnerId;
		}

		public String getServiceId() {
			return serviceId;
		}

		public void setServiceId(String serviceId) {
			this.serviceId = serviceId;
		}

		public String getItemUnit() {
			return itemUnit;
		}

		public void setItemUnit(String itemUnit) {
			this.itemUnit = itemUnit;
		}

		public Double getItemBaseCost() {
			return itemBaseCost;
		}

		public void setItemBaseCost(Double itemBaseCost) {
			this.itemBaseCost = itemBaseCost;
		}

		public Double getItemMinOrder() {
			return itemMinOrder;
		}

		public void setItemMinOrder(Double itemMinOrder) {
			this.itemMinOrder = itemMinOrder;
		}

		public Double getItemMaxCost() {
			return itemMaxCost;
		}

		public void setItemMaxCost(Double itemMaxCost) {
			this.itemMaxCost = itemMaxCost;
		}

		public Double getItemCost() {
			return itemCost;
		}

		public void setItemCost(Double itemCost) {
			this.itemCost = itemCost;
		}

		public Boolean getIsPreOrder() {
			return isPreOrder;
		}

		public void setIsPreOrder(Boolean isPreOrder) {
			this.isPreOrder = isPreOrder;
		}

		public Boolean getIsQuotation() {
			return isQuotation;
		}

		public void setIsQuotation(Boolean isQuotation) {
			this.isQuotation = isQuotation;
		}

		public Boolean getIsActive() {
			return isActive;
		}

		public void setIsActive(Boolean isActive) {
			this.isActive = isActive;
		}

		public String getSuperCategory() {
			return superCategory;
		}

		public void setSuperCategory(String superCategory) {
			this.superCategory = superCategory;
		}

		public String getItemPackCat() {
			return itemPackCat;
		}

		public void setItemPackCat(String itemPackCat) {
			this.itemPackCat = itemPackCat;
		}
	    
	    
}
