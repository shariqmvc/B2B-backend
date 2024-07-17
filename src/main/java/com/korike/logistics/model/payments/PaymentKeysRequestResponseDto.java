package com.korike.logistics.model.payments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentKeysRequestResponseDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("merchant_key")
    private String merchantKey;

    @JsonProperty("merchant_salt")
    private String merchantSalt;

    @JsonProperty("payment_gateway")
    private String payment_gateway;

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    public String getMerchantSalt() {
        return merchantSalt;
    }

    public void setMerchantSalt(String merchantSalt) {
        this.merchantSalt = merchantSalt;
    }

    public String getPayment_gateway() {
        return payment_gateway;
    }

    public void setPayment_gateway(String payment_gateway) {
        this.payment_gateway = payment_gateway;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
