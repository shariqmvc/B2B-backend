package com.korike.logistics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsumerAddresses {

    @JsonProperty("consumer_id")
    private String consumerId;

    @JsonProperty("consumer_address")
    private String consumerAddress;

    @JsonProperty("consumer_address_name")
    private String consumerAddressName;

    @JsonProperty("consumer_address_status")
    private String consumerAddressStatus;

    @JsonProperty("consumer_address_latlong")
    private String consumerAddressStatusLatLong;

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getConsumerAddress() {
        return consumerAddress;
    }

    public void setConsumerAddress(String consumerAddress) {
        this.consumerAddress = consumerAddress;
    }

    public String getConsumerAddressName() {
        return consumerAddressName;
    }

    public void setConsumerAddressName(String consumerAddressName) {
        this.consumerAddressName = consumerAddressName;
    }

    public String getConsumerAddressStatus() {
        return consumerAddressStatus;
    }

    public void setConsumerAddressStatus(String consumerAddressStatus) {
        this.consumerAddressStatus = consumerAddressStatus;
    }

    public String getConsumerAddressStatusLatLong() {
        return consumerAddressStatusLatLong;
    }

    public void setConsumerAddressStatusLatLong(String consumerAddressStatusLatLong) {
        this.consumerAddressStatusLatLong = consumerAddressStatusLatLong;
    }
}
