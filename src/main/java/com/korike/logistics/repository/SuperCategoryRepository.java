package com.korike.logistics.repository;

import com.korike.logistics.entity.CustomerOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korike.logistics.entity.SuperCategory;

import java.util.List;

@Repository
public interface SuperCategoryRepository extends JpaRepository<SuperCategory, Long>{

	SuperCategory findBySuperCatId(Long supCatId);
	@Query(value = "select * from super_category where name = :name", nativeQuery = true)
		/* select * from customer_order_details where date(created_at)  between '2022-04-06' and '2022-04-06' and order_status in ('CREATED','INPROGRESS','COMPLETED','FAILED','CANCELLED') */
	SuperCategory findByName(@Param("name") String superCatName);

}

