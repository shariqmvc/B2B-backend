package com.korike.logistics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korike.logistics.entity.Promo;

@Repository
public interface PromoRepository extends JpaRepository<Promo, Long>{

	@Query(value = "select * from promo where service_id = :service_id or is_active = :isActive", nativeQuery = true)
	/* select * from customer_order_details where date(created_at)  between '2022-04-06' and '2022-04-06' and order_status in ('CREATED','INPROGRESS','COMPLETED','FAILED','CANCELLED') */
	List<Promo> getPromoByFilter(@Param("service_id") String service_id /*, @Param("service_id") String service_id*/, @Param("isActive") Boolean isActive);
	@Query(value = "select * from promo where partner_id is null", nativeQuery = true)
	List<Promo> getPromosWithOutPartner();
}
