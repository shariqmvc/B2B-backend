package com.korike.logistics.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="items")



//@Data
//@NoArgsConstructor
//@AllArgsConstructor

public class Items {

	@Id
	@Column(name="item_id", length=100)
	private String item_id;

	@Column(name="item_title")
	private String item_title;

	@Column(name="item_description")
	private String item_description;


 /*   @OneToOne
   	@JoinColumn(name = "input_material_id", insertable = true, updatable = true) */

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "partner_id", referencedColumnName = "partner_id")
	@JsonBackReference
	private Partner servicePartner;



	@ManyToOne(fetch = FetchType.LAZY, cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	@JoinColumn(name = "service_id")
/*    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id", referencedColumnName = "service_id") */
	private ServiceDefinition serviceDefinition;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "super_cat_id")
	private SuperCategory itemSuperCategory;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cat_id")
	private Category itemCategory;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sub_cat_id")
	private SubCategory itemSubCategory;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_package_category_id")
	private ItemPackageCategory itemPackCat;

	@Column(name="tax")
	private Double tax;

	@Column(name="HSN")
	private String hsnNumber;

	@Column(name="origin")
	private String origin;

	@Column(name="type")
	private String type;

/*    @Column(name="item_sub_category")
    private String subCategory; */

	@Column(name="item_base_quantity")
	private Double item_base_quantity;

	@Column(name="item_unit")
	private String itemUnit;

	@Column(name="item_size")
	private String itemSize;

	@Column(name="item_base_cost")
	private Double itemBaseCost;

	public String getInvoicePath() {
		return invoicePath;
	}

	public void setInvoicePath(String invoicePath) {
		this.invoicePath = invoicePath;
	}

	@Column(name="item_min_order")
	private Double itemMinOrder;

	@Column(name="invoice_path")
	private String invoicePath;

	@Column(name="item_max_order")
	private Double itemMaxCost;

	@Column(name="item_whole_sale_cost")
	private Double itemWholeSaleCost;

	@Column(name="item_retail_cost")
	private Double itemRetailCost;

	@Column(name="is_pre_order")
	private Boolean isPreOrder;

	@Column(name="is_quotation")
	private Boolean isQuotation;

	@Column(name="item_image_path")
	private String itemImagePath;

	@Column(name="is_active")
	private Boolean isActive;

	public Boolean getPreOrder() {
		return isPreOrder;
	}

	public void setPreOrder(Boolean preOrder) {
		isPreOrder = preOrder;
	}

	public Boolean getQuotation() {
		return isQuotation;
	}

	public void setQuotation(Boolean quotation) {
		isQuotation = quotation;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

	public Long getMrp() {
		return mrp;
	}

	public void setMrp(Long mrp) {
		this.mrp = mrp;
	}

	@Column(name="mrp")
	private Long mrp;


	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp createdAt;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.S")
	private Timestamp lastUpdatedAt;
	@Column(name = "created_by", nullable = true)
	private String createdBy;
	@Column(name = "last_updated_by", nullable = true)
	private String lastUpdatedBy;





	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getLastUpdatedAt() {
		return lastUpdatedAt;
	}
	public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}





	public Items() {
		super();
		// TODO Auto-generated constructor stub
	}

/*	public Items(String item_id, String item_title, String item_description, Partner servicePartner,
			ServiceDefinition serviceDefinition, String category, String subCategory, Double item_base_quantity,
			String itemUnit, Double itemBaseCost, Double itemMinOrder, Double itemMaxCost, Double itemCost,
			Boolean isPreOrder, Boolean isQuotation, Boolean isActive) {
		super();
		this.item_id = item_id;
		this.item_title = item_title;
		this.item_description = item_description;
		this.servicePartner = servicePartner;
		this.serviceDefinition = serviceDefinition;
		this.category = category;
		this.subCategory = subCategory;
		this.item_base_quantity = item_base_quantity;
		this.itemUnit = itemUnit;
		this.itemBaseCost = itemBaseCost;
		this.itemMinOrder = itemMinOrder;
		this.itemMaxCost = itemMaxCost;
		this.itemCost = itemCost;
		this.isPreOrder = isPreOrder;
		this.isQuotation = isQuotation;
		this.isActive = isActive;
	} */



	public Items(String item_id, String item_title, String item_description, Partner servicePartner,
				 ServiceDefinition serviceDefinition, SuperCategory itemSuperCategory, Category itemCategory,
				 SubCategory itemSubCategory, ItemPackageCategory itemPackCat, String origin, String type, Double item_base_quantity,
				 String itemUnit, Double itemBaseCost, Double itemMinOrder, Double itemMaxCost, Double itemRetailCost, Double itemWholeSaleCost,
				 Boolean isPreOrder, Boolean isQuotation, String itemImagePath, Boolean isActive, Timestamp createdAt,
				 Timestamp lastUpdatedAt, String createdBy, String lastUpdatedBy) {
		super();
		this.item_id = item_id;
		this.item_title = item_title;
		this.item_description = item_description;
		this.servicePartner = servicePartner;
		this.serviceDefinition = serviceDefinition;
		this.itemSuperCategory = itemSuperCategory;
		this.itemCategory = itemCategory;
		this.itemSubCategory = itemSubCategory;
		this.itemPackCat = itemPackCat;
		this.origin = origin;
		this.type = type;
		//	this.subCategory = subCategory;
		this.item_base_quantity = item_base_quantity;
		this.itemUnit = itemUnit;
		this.itemBaseCost = itemBaseCost;
		this.itemMinOrder = itemMinOrder;
		this.itemMaxCost = itemMaxCost;
		this.itemRetailCost = itemRetailCost;
		this.itemWholeSaleCost = itemWholeSaleCost;
		this.isPreOrder = isPreOrder;
		this.isQuotation = isQuotation;
		this.itemImagePath = itemImagePath;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.lastUpdatedAt = lastUpdatedAt;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public SuperCategory getItemSuperCategory() {
		return itemSuperCategory;
	}
	public void setItemSuperCategory(SuperCategory itemSuperCategory) {
		this.itemSuperCategory = itemSuperCategory;
	}
	public Category getItemCategory() {
		return itemCategory;
	}
	public void setItemCategory(Category itemCategory) {
		this.itemCategory = itemCategory;
	}
	public SubCategory getItemSubCategory() {
		return itemSubCategory;
	}
	public void setItemSubCategory(SubCategory itemSubCategory) {
		this.itemSubCategory = itemSubCategory;
	}
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

	public Partner getServicePartner() {
		return servicePartner;
	}

	public void setServicePartner(Partner servicePartner) {
		this.servicePartner = servicePartner;
	}

	public ServiceDefinition getServiceDefinition() {
		return serviceDefinition;
	}

	public void setServiceDefinition(ServiceDefinition serviceDefinition) {
		this.serviceDefinition = serviceDefinition;
	}



/*	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	} */

	public Double getItem_base_quantity() {
		return item_base_quantity;
	}

	public void setItem_base_quantity(Double item_base_quantity) {
		this.item_base_quantity = item_base_quantity;
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


	public Double getItemWholeSaleCost() {
		return itemWholeSaleCost;
	}
	public void setItemWholeSaleCost(Double itemWholeSaleCost) {
		this.itemWholeSaleCost = itemWholeSaleCost;
	}
	public Double getItemRetailCost() {
		return itemRetailCost;
	}
	public void setItemRetailCost(Double itemRetailCost) {
		this.itemRetailCost = itemRetailCost;
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
	public String getItemImagePath() {
		return itemImagePath;
	}
	public void setItemImagePath(String itemImagePath) {
		this.itemImagePath = itemImagePath;
	}
	public ItemPackageCategory getItemPackCat() {
		return itemPackCat;
	}
	public void setItemPackCat(ItemPackageCategory itemPackCat) {
		this.itemPackCat = itemPackCat;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public String getHsnNumber() {
		return hsnNumber;
	}
	public void setHsnNumber(String hsnNumber) {
		this.hsnNumber = hsnNumber;
	}

	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}
}
