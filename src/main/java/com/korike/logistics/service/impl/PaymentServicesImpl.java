package com.korike.logistics.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korike.logistics.entity.PaymentInformation;
import com.korike.logistics.exception.ApiErrorCode;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.model.payments.PaymentCallback;
import com.korike.logistics.model.payments.PaymentHashes;
import com.korike.logistics.model.payments.PaymentModel;
import com.korike.logistics.model.payments.PaymentStatus;
import com.korike.logistics.repository.PaymentInformationRepository;
import com.korike.logistics.service.PaymentServices;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentServicesImpl implements PaymentServices {

    @Autowired
    PaymentInformationRepository paymentInformationRepository;

    private static Logger logger = Logger.getLogger(PaymentServicesImpl.class);
    @Override
    public PaymentInformation createUpdatePaymentInformationEntry(PaymentModel paymentModel, PaymentInformation paymentInformation) {
        if(paymentInformation==null){
            logger.info("Creating new paymentInformation entry");
            PaymentInformation paymentInformationToSave = new PaymentInformation();
            paymentInformationToSave.setAmount(Double.valueOf(paymentModel.getAmount()));
            paymentInformationToSave.setEmail(paymentModel.getEmail());
            paymentInformationToSave.setName(paymentModel.getName());
            paymentInformationToSave.setPaymentDate(new Date());
            paymentInformationToSave.setPaymentStatus(PaymentStatus.Pending);
            paymentInformationToSave.setTxnId(paymentModel.getTxnId());
            paymentInformationToSave.setPhone(paymentModel.getPhone());
            paymentInformationToSave.setProductInfo(paymentModel.getProductInfo());
            ObjectMapper hashMapper = new ObjectMapper();
            String hashes = null;
            try {
                hashes = hashMapper.writeValueAsString(hashMapper);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            paymentInformationToSave.setHash(hashes);
            paymentInformation = paymentInformationToSave;
        }
        else{
            paymentInformation.setAmount(Double.valueOf(paymentModel.getAmount()));
            paymentInformation.setEmail(paymentModel.getEmail());
            paymentInformation.setName(paymentModel.getName());
            paymentInformation.setPaymentDate(new Date());
            paymentInformation.setPaymentStatus(PaymentStatus.Pending);
            paymentInformation.setTxnId(paymentModel.getTxnId());
            paymentInformation.setPhone(paymentModel.getPhone());
            paymentInformation.setProductInfo(paymentModel.getProductInfo());
            ObjectMapper hashMapper = new ObjectMapper();
            String hashes = null;
            try {
                hashes = hashMapper.writeValueAsString(hashMapper);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            paymentInformation.setHash(hashes);
        }
        try{
            paymentInformation = paymentInformationRepository.save(paymentInformation);
            return paymentInformation;

        }catch(Exception e){
            logger.error("Error updating payment information");
            throw new KorikeException("Error updating payment information"+ ApiErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public PaymentInformation getPaymentInformationByTransId(String paymentTransactionId) {

        if(paymentTransactionId!=null) {
            return paymentInformationRepository.getPaymentInformationByTxId(paymentTransactionId);
        }else return null;
    }

    @Override
    public String payuCallback(PaymentCallback paymentResponse) {
        String msg = "Transaction failed.";
        PaymentInformation payment = paymentInformationRepository.getPaymentInformationByTxId(paymentResponse.getTxnid());
        if(payment != null) {
            //TODO validate the hash
            PaymentStatus paymentStatus = null;
            if(paymentResponse.getStatus().equals("failure")){
                paymentStatus = PaymentStatus.Failed;
            }else if(paymentResponse.getStatus().equals("success")) {
                paymentStatus = PaymentStatus.Success;
                msg = "Transaction success";
            }
            payment.setPaymentStatus(paymentStatus);
            payment.setMihpayId(paymentResponse.getMihpayid());
            payment.setMode(paymentResponse.getMode());
            paymentInformationRepository.save(payment);
        }
        return msg;
    }
}
