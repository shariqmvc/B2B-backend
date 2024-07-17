package com.korike.logistics.model.partner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.korike.logistics.entity.Partner;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServicePartnerResponseDto {

    @JsonProperty("partner_id")
    private String partnerId;

    @JsonProperty("partner_details")
    private PartnerDetails partner;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public PartnerDetails getPartner() {
        return partner;
    }

    public void setPartner(PartnerDetails partner) {
        this.partner = partner;
    }
}
