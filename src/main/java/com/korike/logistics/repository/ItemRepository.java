package com.korike.logistics.repository;

import java.util.List;

import com.korike.logistics.model.items.ItemsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korike.logistics.entity.Items;

@Repository
public interface ItemRepository extends JpaRepository<Items, String> {
    @Query(value = "select * from items where partner_id = :partner_id" , nativeQuery = true)
    List<Items> getItemsByPartnerId(@Param("partner_id") String partnerId);
    @Query(value = "select * from items where order_id = :order_id" , nativeQuery = true)
    List<Items> getItemsByOrderId(@Param("order_id") String orderId);
    @Query(value = "select * from items where item_title like '%:item_title%'" , nativeQuery = true)
    List<Items> getItemsByItemTitle(@Param("item_title") String itemTitle);
    @Query(value = "select * from items where service_id = :service_id and type = 'PreOrder'", nativeQuery = true)
    public List<Items> getItemsByServiceId(@Param("service_id") String service_id);
//    @Query(value = "select * from items where item_id = :item_id or partner_id = :partner_id or service_id = :service_id or cat_id = :cat_id or sub_cat_id = :sub_cat_id or item_unit = :item_unit or item_cost = :item_cost or item_min_order = :item_min_order or item_max_order = :item_max_order or is_pre_order = :is_pre_order or is_quotation = :is_quotation or super_cat_id = :super_cat_id or item_package_category_id = :item_package_category_id or is_active = :is_active" , nativeQuery = true)
//    List<Items> getItemsByFilter(@Param("item_id") String item_id, @Param("partner_id") String partner_id,
//                                 @Param("service_id") String service_id,
//                                 @Param("cat_id") Long cat_id, @Param("sub_cat_id") Long sub_cat_id, @Param("item_unit") String item_unit, @Param("item_cost") Double item_cost,
//                                 @Param("item_min_order") Double item_min_order, @Param("item_max_order") Double item_max_order, @Param("is_pre_order") Boolean is_pre_order, @Param("is_quotation") Boolean is_quotation,@Param("super_cat_id") Long super_cat_id,
//                                 @Param("item_package_category_id") Long item_package_category_id, @Param("is_active") Boolean is_active);

    @Query(value = "select * from items where service_id = :service_id", nativeQuery = true)
    List<Items> getItemsByFilter(@Param("service_id") String service_id);

    @Query(value = "select * from items where item_id in (:itemlist)", nativeQuery = true)
    List<Items> getItemsByItemIds(@Param("itemlist") List<String> itemlist);

}
