package com.korike.logistics.model.partner;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PartnerDeclinedOrdersDto {

    @JsonProperty("decline_id")
    private String declineId;

    @JsonProperty("partner_id")
    private String partnerId;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("partner_location")
    private String partnerLocation;

    public String getPartnerLocation() {
        return partnerLocation;
    }

    public void setPartnerLocation(String partnerLocation) {
        this.partnerLocation = partnerLocation;
    }

    public String getDeclineId() {
        return declineId;
    }

    public void setDeclineId(String declineId) {
        this.declineId = declineId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
