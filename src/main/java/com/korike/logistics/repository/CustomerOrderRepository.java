package com.korike.logistics.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korike.logistics.entity.CustomerOrderDetails;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrderDetails, String> {

	List<CustomerOrderDetails> findByCreatedAtBetween(Timestamp start, Timestamp end);
	List<CustomerOrderDetails> findByCreatedAtBetweenAndOrderStatus(Timestamp start, Timestamp end,String status);


	@Query(value = "select * from customer_order_details where date(created_at)  between :startDate and :endDate and order_status = :status", nativeQuery = true)
	List<CustomerOrderDetails> getOrdersByDateAndStatus(@Param("startDate") String startDate, @Param("endDate") String endDate,@Param("status") String status);

	@Query(value = "select * from customer_order_details where order_id= :order_id", nativeQuery = true)
	Optional<CustomerOrderDetails> getOrderById(@Param("order_id") String order_id);

	@Query(value = "select * from customer_order_details where date(created_at)  between :startDate and :endDate", nativeQuery = true)
		/* select * from customer_order_details where date(created_at)  between '2022-04-06' and '2022-04-06' and order_status in ('CREATED','INPROGRESS','COMPLETED','FAILED','CANCELLED') */
	List<CustomerOrderDetails> getOrdersByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);

	@Query(value = "select * from customer_order_details order by created_at desc", nativeQuery = true)
	List<CustomerOrderDetails> getOrders();

	@Query(value = "select * from customer_order_details where order_status = :status", nativeQuery = true)
	List<CustomerOrderDetails> getOrdersByStatus(@Param("status") String status);

	@Query(value = "select * from customer_order_details where service_id = :s_id and order_status = 'CREATED'", nativeQuery = true)
	List<CustomerOrderDetails> getOpenOrdersBySid(@Param("s_id") String sid);
	/*select * from customer_order_details where  order_status = :status" */
	@Query(value = "select * from customer_order_details where partner_id = :partner_id or order_id IN (select order_id from partner_declined_orders where partner_id = :partner_id);", nativeQuery = true)
	List<CustomerOrderDetails> getOrdersByPartner(@Param("partner_id") String partner_id);
}
