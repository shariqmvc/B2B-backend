package com.korike.logistics.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korike.logistics.entity.PaymentInformation;
import com.korike.logistics.entity.PaymentKeys;
import com.korike.logistics.model.payments.PaymentDetails;
import com.korike.logistics.model.payments.PaymentHashes;
import com.korike.logistics.model.payments.PaymentModel;
import com.korike.logistics.repository.PaymentKeysRepository;
import com.korike.logistics.service.impl.PaymentServicesImpl;
import lombok.extern.flogger.Flogger;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PaymentUtil {
    private static Logger logger = Logger.getLogger(PaymentUtil.class);
    @Autowired
    PaymentKeysRepository paymentKeysRepository;

    private static final String sUrl = "https://korikelogistics.com:8443/api/consumer/payment-response";

    private static final String fUrl = "https://korikelogistics:8443/api/consumer/payment-response";

    public static PaymentModel populatePaymentDetail(PaymentModel paymentInformation) throws JSONException, JsonProcessingException {
        String hashString = "";
        Random rand = new Random();
        String randomId = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
        String txnId = "Dev" + hashCal("SHA-256", randomId).substring(0, 12);
        //paymentInformation.setTxnId(txnId);
        PaymentHashes payuHashes = new PaymentHashes();
        //String otherPostParamSeq = "phone|surl|furl|lastname|curl|address1|address2|city|state|country|zipcode|pg";
        String hashSequence_payment_hash = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5||||||SALT";
        hashSequence_payment_hash = hashSequence_payment_hash.replace("SALT", paymentInformation.getSalt());
        hashSequence_payment_hash = hashSequence_payment_hash.replace("key", paymentInformation.getKey());
        hashSequence_payment_hash = hashSequence_payment_hash.replace("txnid", paymentInformation.getTxnId());
        hashSequence_payment_hash = hashSequence_payment_hash.replace("amount", paymentInformation.getAmount());
        ObjectMapper productInfoMapper = new ObjectMapper();
        hashSequence_payment_hash = hashSequence_payment_hash.replace("productinfo", productInfoMapper.writeValueAsString(paymentInformation.getProductInfo()));
        hashSequence_payment_hash = hashSequence_payment_hash.replace("firstname", paymentInformation.getName());
        hashSequence_payment_hash = hashSequence_payment_hash.replace("email", paymentInformation.getEmail());
        hashSequence_payment_hash = hashSequence_payment_hash.replace("udf1", "");
        hashSequence_payment_hash = hashSequence_payment_hash.replace("udf2", "");
        hashSequence_payment_hash = hashSequence_payment_hash.replace("udf3", "");
        hashSequence_payment_hash = hashSequence_payment_hash.replace("udf4", "");
        hashSequence_payment_hash = hashSequence_payment_hash.replace("udf5", "");
        logger.info("hash === "+hashSequence_payment_hash);
        payuHashes.setPayment_hash(hashCal("SHA-512", hashSequence_payment_hash));

        String hashSequence_vas_for_mobile_sdk_hash = "key|command|var1|";
        hashSequence_vas_for_mobile_sdk_hash = hashSequence_vas_for_mobile_sdk_hash.concat(paymentInformation.getSalt());
        hashSequence_vas_for_mobile_sdk_hash = hashSequence_vas_for_mobile_sdk_hash.replace("key", paymentInformation.getKey());
        hashSequence_vas_for_mobile_sdk_hash = hashSequence_vas_for_mobile_sdk_hash.replace("command", "vas_for_mobile_sdk_hash");
        hashSequence_vas_for_mobile_sdk_hash = hashSequence_vas_for_mobile_sdk_hash.replace("var1", "default");
        payuHashes.setVas_for_mobile_sdk_hash(hashCal("SHA-512", hashSequence_vas_for_mobile_sdk_hash));

        String hashSequence_payment_related_details_for_mobile_sdk_hash = "key|command|var1|";
        hashSequence_payment_related_details_for_mobile_sdk_hash = hashSequence_payment_related_details_for_mobile_sdk_hash.concat(paymentInformation.getSalt());
        hashSequence_payment_related_details_for_mobile_sdk_hash = hashSequence_payment_related_details_for_mobile_sdk_hash.replace("key", paymentInformation.getKey());
        hashSequence_payment_related_details_for_mobile_sdk_hash = hashSequence_payment_related_details_for_mobile_sdk_hash.replace("command", "payment_related_details_for_mobile_sdk_hash");
        hashSequence_payment_related_details_for_mobile_sdk_hash = hashSequence_payment_related_details_for_mobile_sdk_hash.replace("var1", "default");
        payuHashes.setPayment_related_details_for_mobile_sdk_hash(hashCal("SHA-512", hashSequence_payment_related_details_for_mobile_sdk_hash));

        paymentInformation.setHash(payuHashes);
        paymentInformation.setfUrl(fUrl);
        paymentInformation.setsUrl(sUrl);
        paymentInformation.setKey(paymentInformation.getKey());
        return paymentInformation;
    }

    public static String hashCal(String type, String str) {
        byte[] hashseq = str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }

        } catch (NoSuchAlgorithmException nsae) {

        }
        return hexString.toString();
    }

}
