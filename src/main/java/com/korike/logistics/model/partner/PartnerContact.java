package com.korike.logistics.model.partner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerContact {

    @JsonProperty("partner_phones")
    List<String> partner_phones;

    @JsonProperty("partner_emails")
    List<String> partner_emails;

    public List<String> getPartner_phones() {
        return partner_phones;
    }

    public void setPartner_phones(List<String> partner_phones) {
        this.partner_phones = partner_phones;
    }

    public List<String> getPartner_emails() {
        return partner_emails;
    }

    public void setPartner_emails(List<String> partner_emails) {
        this.partner_emails = partner_emails;
    }
}
