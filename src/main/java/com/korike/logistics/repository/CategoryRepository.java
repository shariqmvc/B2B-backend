package com.korike.logistics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korike.logistics.entity.Category;
import com.korike.logistics.entity.Items;
import com.korike.logistics.entity.SuperCategory;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	Category findByCatId(Long catId);
//	List<Category> findbysuperCategory(SuperCategory supCat);
	
	@Query(value = "select * from category where super_cat_id = :SupCatId" , nativeQuery = true)
    List<Category> getCatBySupCatId(@Param("SupCatId") Long SupCatId);

	@Query(value = "select * from category where name = :name" , nativeQuery = true)
	Category findByName(@Param("name") String name);
}
