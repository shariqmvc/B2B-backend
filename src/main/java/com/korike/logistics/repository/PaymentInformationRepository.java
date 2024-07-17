package com.korike.logistics.repository;

import com.korike.logistics.entity.PaymentInformation;
import com.korike.logistics.entity.PaymentKeys;
import com.korike.logistics.entity.SuperCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInformationRepository extends JpaRepository<PaymentInformation, String> {

    @Query(value = "select * from payment_information where txn_id = :txn_id", nativeQuery = true)
    public PaymentInformation getPaymentInformationByTxId(@Param("txn_id") String txnId);

}
