package com.korike.logistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korike.logistics.entity.PaymentTransactionDetails;

import java.util.List;

@Repository
public interface PaymentTransactionDetailRepository extends JpaRepository<PaymentTransactionDetails, String>{

    @Query(value = "select * from payment_transaction_details where order_id = :order_id", nativeQuery = true)
    List<PaymentTransactionDetails> fetchTransactionDetailsByOrderId(@Param("order_id") String order_id);
}
