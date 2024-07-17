package com.korike.logistics.repository;

import com.korike.logistics.entity.PartnerDeclinedOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerDeclinedOrdersRepo  extends JpaRepository<PartnerDeclinedOrders, String> {

    @Query(value = "select * from partner_declined_orders where partner_id = :partner_id", nativeQuery = true)
    public List<PartnerDeclinedOrders> getDeclinedOrdersByPartner(@Param("partner_id") String partner_id);

    @Query(value = "select * from partner_declined_orders where order_id = :order_id", nativeQuery = true)
    public List<PartnerDeclinedOrders> getDeclinedOrdersByOrder(@Param("order_id") String order_id);

    @Query(value = "select * from partner_declined_orders where order_id = :order_id and partner_id = :partner_id", nativeQuery = true)
    public List<PartnerDeclinedOrders> getDeclinedOrdersByOrderAndPartner(@Param("partner_id") String partner_id, @Param("order_id") String order_id);

}
