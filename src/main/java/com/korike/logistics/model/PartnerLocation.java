package com.korike.logistics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerLocation {

    @JsonProperty("partner_latlong")
    private String partnerLatLong;

    @JsonProperty("partner_address")
    private String partnerAddress;

    public String getPartnerLatLong() {
        return partnerLatLong;
    }

    public void setPartnerLatLong(String partnerLatLong) {
        this.partnerLatLong = partnerLatLong;
    }

    public String getPartnerAddress() {
        return partnerAddress;
    }

    public void setPartnerAddress(String partnerAddress) {
        this.partnerAddress = partnerAddress;
    }
}
