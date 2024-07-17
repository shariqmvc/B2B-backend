package com.korike.logistics.service;

import com.korike.logistics.entity.PaymentInformation;
import com.korike.logistics.model.payments.PaymentCallback;
import com.korike.logistics.model.payments.PaymentKeysRequestResponseDto;
import com.korike.logistics.model.payments.PaymentModel;
import com.korike.logistics.repository.PaymentInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface PaymentServices {

    public PaymentInformation createUpdatePaymentInformationEntry(PaymentModel paymentModel, PaymentInformation paymentInformation);
    public PaymentInformation getPaymentInformationByTransId(String paymentTransactionId);
    public String payuCallback(PaymentCallback paymentResponse);

}
