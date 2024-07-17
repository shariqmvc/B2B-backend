package com.korike.logistics.repository;

import com.korike.logistics.entity.*;
import com.korike.logistics.model.partner.PartnerDetails;
import com.korike.logistics.model.services.ServiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicePartnerRepository extends JpaRepository<Partner, String>{
        Partner findByPartnerId(String partnerId);
        @Query(value = "select * from partner where user_id = :user_id", nativeQuery = true)
        List<Partner> getPartnerByUserId(@Param("user_id") String user_id);

        @Query(value = "select * from partner where service_id = :service_id", nativeQuery = true)
        List<Partner> getPartnersByService(@Param("service_id") String service_id);

        @Query(value = "select * from partner where service_id = :service_id and user_id = :user_id", nativeQuery = true)
        List<Partner> getPartnersByUserByService(@Param("service_id") String service_id, @Param("user_id") String user_id);

        @Query(value = "select * from customer_order_details where partner_id = :partner_id", nativeQuery = true)
        List<CustomerOrderDetails> getOrdersByPartner(@Param("partner_id") String partner_id);

        @Query(value = "select partner_files from partner_partner_files where partner_partner_id = :partner_id", nativeQuery = true)
        List<String> getPartnerFiles(@Param("partner_id") String partner_id);


}