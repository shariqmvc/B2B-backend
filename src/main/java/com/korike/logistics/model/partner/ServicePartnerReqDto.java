package com.korike.logistics.model.partner;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.korike.logistics.entity.Partner;
import com.korike.logistics.entity.ServiceDefinition;
import com.korike.logistics.entity.User;

import javax.persistence.*;
import javax.servlet.http.Part;
import java.sql.Timestamp;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServicePartnerReqDto {

        @JsonProperty("partner_details")
        private PartnerDetails partnerDetails;

        public PartnerDetails getPartner() {
                return partnerDetails;
        }

        public void setPartner(PartnerDetails partnerDetails) {
                this.partnerDetails = partnerDetails;
        }


}



