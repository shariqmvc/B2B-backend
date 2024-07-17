package com.korike.logistics.repository;

import com.korike.logistics.entity.CustomerOrderDetails;
import com.korike.logistics.entity.Partner;
import com.korike.logistics.entity.PaymentKeys;
import com.korike.logistics.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentKeysRepository extends JpaRepository<PaymentKeys, String>{


    @Query(value = "select * from payment_keys where payment_gateway = :payment_gateway", nativeQuery = true)
    List<PaymentKeys> getPaymentKeyByGateway(@Param("payment_gateway") String payment_gateway);

}
