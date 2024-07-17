package com.korike.logistics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsumerDetails {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("consumer_phone")
    private String consumerPhone;

    @JsonProperty("consumer_email")
    private String consumerEmail;

    @JsonProperty("consumer_addresses")
    private List<ConsumerAddresses> consumerAddresses;

    @JsonProperty("consumer_kyc")
    private List<Kyc> consumerKyc;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConsumerPhone() {
        return consumerPhone;
    }

    public void setConsumerPhone(String consumerPhone) {
        this.consumerPhone = consumerPhone;
    }

    public String getConsumerEmail() {
        return consumerEmail;
    }

    public void setConsumerEmail(String consumerEmail) {
        this.consumerEmail = consumerEmail;
    }

    public List<ConsumerAddresses> getConsumerAddresses() {
        return consumerAddresses;
    }

    public void setConsumerAddresses(List<ConsumerAddresses> consumerAddresses) {
        this.consumerAddresses = consumerAddresses;
    }

    public List<Kyc> getConsumerKyc() {
        return consumerKyc;
    }

    public void setConsumerKyc(List<Kyc> consumerKyc) {
        this.consumerKyc = consumerKyc;
    }
}
