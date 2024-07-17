package com.korike.logistics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.korike.logistics.model.InvoiceResDto;
import com.korike.logistics.model.orders.OrderDetails;
import com.korike.logistics.model.orders.OrderResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface InvoiceService {
    public InvoiceResDto generateInvoice(OrderResponseDto orderResponseDto) throws JsonProcessingException;
    public double getTaxesForItems(OrderResponseDto orderResponseDto) throws JsonProcessingException ;


    double getDiscountForItems(OrderResponseDto orderResponseDto) throws JsonProcessingException;

    double getDeliveryCharges(OrderResponseDto orderResponseDto) throws JsonProcessingException;

    String generateInvoicePath(OrderDetails orderDetails) throws JsonProcessingException;
}
