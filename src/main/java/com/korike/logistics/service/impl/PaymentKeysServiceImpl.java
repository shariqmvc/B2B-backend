package com.korike.logistics.service.impl;

import com.korike.logistics.entity.PaymentKeys;
import com.korike.logistics.exception.ApiErrorCode;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.model.payments.PaymentKeysRequestResponseDto;
import com.korike.logistics.repository.PaymentKeysRepository;
import com.korike.logistics.service.PaymentKeysService;
import com.korike.logistics.util.CommonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentKeysServiceImpl implements PaymentKeysService {
    private static Logger logger = Logger.getLogger(PaymentKeysServiceImpl.class);

    @Autowired
    PaymentKeysRepository paymentKeysRepository;

    @Override
    public PaymentKeysRequestResponseDto createPaymentKeyEntry(PaymentKeysRequestResponseDto paymentKeysRequestResponseDto) {
        PaymentKeys paymentKeys = new PaymentKeys();
        paymentKeys.setId(UUID.randomUUID().toString());
        paymentKeys.setKey(paymentKeysRequestResponseDto.getMerchantKey());
        paymentKeys.setSalt(paymentKeysRequestResponseDto.getMerchantSalt());
        paymentKeys.setPayment_gateway(paymentKeysRequestResponseDto.getPayment_gateway());
        try{
            paymentKeys = paymentKeysRepository.save(paymentKeys);
            PaymentKeysRequestResponseDto paymentKeysRequestResponseDtoResponse = new PaymentKeysRequestResponseDto();
            paymentKeysRequestResponseDtoResponse.setId(paymentKeys.getId());
            return paymentKeysRequestResponseDtoResponse;
        }catch (Exception e){
            logger.error("Exception occured in creating payment key entry." + CommonUtils.printException(e));
            throw new KorikeException("Exception occured in creating payment key entry."+ ApiErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public PaymentKeysRequestResponseDto updatePaymentKeyEntry(PaymentKeysRequestResponseDto paymentKeysRequestResponseDto, PaymentKeys fetchedPaymentKeys) {

        fetchedPaymentKeys.setKey(paymentKeysRequestResponseDto.getMerchantKey());
        fetchedPaymentKeys.setSalt(paymentKeysRequestResponseDto.getMerchantSalt());
        fetchedPaymentKeys.setPayment_gateway(paymentKeysRequestResponseDto.getPayment_gateway());

        try{
           fetchedPaymentKeys = paymentKeysRepository.save(fetchedPaymentKeys);

           PaymentKeysRequestResponseDto paymentKeysRequestResponseDtoResponse = new PaymentKeysRequestResponseDto();
           paymentKeysRequestResponseDtoResponse.setPayment_gateway(fetchedPaymentKeys.getPayment_gateway());
           paymentKeysRequestResponseDtoResponse.setMerchantKey(fetchedPaymentKeys.getKey());
           paymentKeysRequestResponseDtoResponse.setMerchantSalt(fetchedPaymentKeys.getSalt());
           paymentKeysRequestResponseDtoResponse.setId(fetchedPaymentKeys.getId());
           return paymentKeysRequestResponseDtoResponse;
        }
        catch(Exception e){
            logger.error("Exception occured in updating payment key entry." + CommonUtils.printException(e));
            throw new KorikeException("Exception occured in updating payment key entry."+ ApiErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<PaymentKeysRequestResponseDto> getPaymentKeyByFilter(PaymentKeysRequestResponseDto paymentKeysRequestResponseDto){
        return null;
    }
}
