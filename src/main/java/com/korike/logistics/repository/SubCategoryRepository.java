package com.korike.logistics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korike.logistics.entity.Category;
import com.korike.logistics.entity.SubCategory;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long>{
	SubCategory findBySubCatId(Long subCatId);
//	List<SubCategory> findByCategory(Category cat);
	
	@Query(value = "select * from sub_category where cat_id = :catId" , nativeQuery = true)
    List<SubCategory> getSubCatByCatId(@Param("catId") Long catId);
	@Query(value = "select * from sub_category where name = :name" , nativeQuery = true)
	SubCategory findByName(@Param("name") String name);
}
