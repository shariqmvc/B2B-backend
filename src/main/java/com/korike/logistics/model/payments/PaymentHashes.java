package com.korike.logistics.model.payments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentHashes {

    @JsonProperty("payment_hash")
    private String payment_hash;

    @JsonProperty("vas_for_mobile_sdk_hash")
    private String vas_for_mobile_sdk_hash;

    @JsonProperty("payment_related_details_for_mobile_sdk_hash")
    private String payment_related_details_for_mobile_sdk_hash;

    public String getPayment_hash() {
        return payment_hash;
    }

    public void setPayment_hash(String payment_hash) {
        this.payment_hash = payment_hash;
    }

    public String getVas_for_mobile_sdk_hash() {
        return vas_for_mobile_sdk_hash;
    }

    public void setVas_for_mobile_sdk_hash(String vas_for_mobile_sdk_hash) {
        this.vas_for_mobile_sdk_hash = vas_for_mobile_sdk_hash;
    }

    public String getPayment_related_details_for_mobile_sdk_hash() {
        return payment_related_details_for_mobile_sdk_hash;
    }

    public void setPayment_related_details_for_mobile_sdk_hash(String payment_related_details_for_mobile_sdk_hash) {
        this.payment_related_details_for_mobile_sdk_hash = payment_related_details_for_mobile_sdk_hash;
    }
}
