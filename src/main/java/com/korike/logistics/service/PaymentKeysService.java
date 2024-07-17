package com.korike.logistics.service;

import com.korike.logistics.entity.*;
import com.korike.logistics.model.BillingModelDto;
import com.korike.logistics.model.payments.PaymentKeysRequestResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentKeysService {
    public PaymentKeysRequestResponseDto createPaymentKeyEntry(PaymentKeysRequestResponseDto paymentKeysRequestResponseDto);
    public PaymentKeysRequestResponseDto updatePaymentKeyEntry(PaymentKeysRequestResponseDto paymentKeysRequestResponseDto, PaymentKeys fetchedPaymentKeys);
    List<PaymentKeysRequestResponseDto> getPaymentKeyByFilter(PaymentKeysRequestResponseDto paymentKeysRequestResponseDto);
}
