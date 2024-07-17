package com.korike.logistics.repository;

import com.korike.logistics.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korike.logistics.entity.ItemPackageCategory;

import java.util.List;

@Repository
public interface ItemPackageCategoryRepository extends JpaRepository<ItemPackageCategory, Long>{
	
	ItemPackageCategory findByItemPackageCategoryId(Long id);

	@Query(value = "select * from item_package_category where name = :name" , nativeQuery = true)
	ItemPackageCategory findByName(@Param("name") String name);

}
