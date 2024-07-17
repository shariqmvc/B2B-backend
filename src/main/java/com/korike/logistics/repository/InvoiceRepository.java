package com.korike.logistics.repository;

import com.korike.logistics.entity.CustomerOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korike.logistics.entity.Invoice;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String>{

    @Query(value = "select * from invoice where order_id = :order_id", nativeQuery = true)
    Invoice getInvoiceByOrderId(@Param("order_id") String partner_id);

}
