
package com.korike.logistics.model.services;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SPromo {

    @JsonProperty("promo_name")
    private String promoName;
    @JsonProperty("promo_id")
    private String promoId;
   

    @JsonProperty("promo_name")
    public String getPromoName() {
        return promoName;
    }

    @JsonProperty("promo_name")
    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    @JsonProperty("promo_id")
    public String getPromoId() {
        return promoId;
    }

    @JsonProperty("promo_id")
    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

   

}
