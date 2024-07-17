
package com.korike.logistics.model.services;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class SNearest {

    @JsonProperty("partner_id")
    private String partnerId;
    @JsonProperty("location")
    private String location;
    @JsonProperty("eta")
    private String eta;
   

    @JsonProperty("partner_id")
    public String getPartnerId() {
        return partnerId;
    }

    @JsonProperty("partner_id")
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("eta")
    public String getEta() {
        return eta;
    }

    @JsonProperty("eta")
    public void setEta(String eta) {
        this.eta = eta;
    }

  

}
