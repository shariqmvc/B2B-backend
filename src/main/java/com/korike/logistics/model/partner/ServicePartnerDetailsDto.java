package com.korike.logistics.model.partner;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServicePartnerDetailsDto {

        @JsonProperty("partner_id")
        private String partnerId;

        @JsonProperty("partner_type")
        private String partnerType;

        @JsonProperty("partner_details")
        private PartnerDetails partnerDetails;

        @JsonProperty("partner_files")
        private Byte[] partnerFiles;

        @JsonProperty("partner_status")
        private String partnerStatus;

        @JsonProperty("is_active")
        private String isActive;

        public String getPartnerId() {
                return partnerId;
        }

        public void setPartnerId(String partnerId) {
                this.partnerId = partnerId;
        }

        public String getPartnerType() {
                return partnerType;
        }

        public void setPartnerType(String partnerType) {
                this.partnerType = partnerType;
        }

        public PartnerDetails getPartnerDetails() {
                return partnerDetails;
        }

        public void setPartnerDetails(PartnerDetails partnerDetails) {
                this.partnerDetails = partnerDetails;
        }

        public String getPartnerStatus() {
                return partnerStatus;
        }

        public void setPartnerStatus(String partnerStatus) {
                this.partnerStatus = partnerStatus;
        }

        public String getIsActive() {
                return isActive;
        }

        public void setIsActive(String isActive) {
                this.isActive = isActive;
        }

        public Byte[] getPartnerFiles() {
                return partnerFiles;
        }

        public void setPartnerFiles(Byte[] partnerFiles) {
                this.partnerFiles = partnerFiles;
        }
}


