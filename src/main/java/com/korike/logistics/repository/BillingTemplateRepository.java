package com.korike.logistics.repository;

import com.korike.logistics.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korike.logistics.entity.BillingTemplate;

import java.util.List;

@Repository
public interface BillingTemplateRepository extends JpaRepository<BillingTemplate, Long>{

    @Query(value = "select * from billing_template where partner_id = :partner_id" , nativeQuery = true)
    List<BillingTemplate> getBillingTemplatesByPartnerId(@Param("partner_id") String partnerId);

    @Query(value = "select * from billing_template where partner_id = :partner_id and is_active = :is_active", nativeQuery = true)
    List<BillingTemplate> getBillingTemplatesListByFilter(@Param("partner_id") String partner_id, @Param("is_active") Boolean is_active);

}
