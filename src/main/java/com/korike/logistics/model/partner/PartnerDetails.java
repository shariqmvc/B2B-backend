package com.korike.logistics.model.partner;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.korike.logistics.model.PartnerLocation;

import java.sql.Timestamp;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerDetails {

    @JsonProperty("partner_id")
    private String partnerId;

    @JsonProperty("partner_contact")
    private PartnerContact partnerContact;

    public PartnerContact getPartnerContact() {
        return partnerContact;
    }

    public void setPartnerContact(PartnerContact partnerContact) {
        this.partnerContact = partnerContact;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerSid() {
        return partnerSid;
    }

    public void setPartnerSid(String partnerSid) {
        this.partnerSid = partnerSid;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerDescription() {
        return partnerDescription;
    }

    public void setPartnerDescription(String partnerDescription) {
        this.partnerDescription = partnerDescription;
    }

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public List<String> getPartnerFiles() {
        return partnerFiles;
    }

    public void setPartnerFiles(List<String> partnerFiles) {
        this.partnerFiles = partnerFiles;
    }

    public String getPartnerStatus() {
        return partnerStatus;
    }

    public void setPartnerStatus(String partnerStatus) {
        this.partnerStatus = partnerStatus;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getPartnerGst() {
        return partnerGst;
    }

    public void setPartnerGst(String partnerGst) {
        this.partnerGst = partnerGst;
    }

    public String getPartnerPan() {
        return partnerPan;
    }

    public void setPartnerPan(String partnerPan) {
        this.partnerPan = partnerPan;
    }

    @JsonProperty("partner_sid")
    private String partnerSid;

    @JsonProperty("partner_username")
    private String partnerUsername;

    @JsonProperty("partner_name")
    private String partnerName;

    @JsonProperty("partner_description")
    private String partnerDescription;

    @JsonProperty("partner_type")
    private String partnerType;

    @JsonProperty("partner_files")
    private List<String> partnerFiles;

    @JsonProperty("partner_status")
    private String partnerStatus;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("partner_gst")
    private String partnerGst;

    @JsonProperty("partner_reg")
    private String partnerReg;

    @JsonProperty("partner_pan")
    private String partnerPan;

    public PartnerLocation getPartnerLocation() {
        return partnerLocation;
    }

    public void setPartnerLocation(PartnerLocation partnerLocation) {
        this.partnerLocation = partnerLocation;
    }

    @JsonProperty("partner_location")
    private PartnerLocation partnerLocation;

    @JsonProperty("created_at")
    private Timestamp createdAt;

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getPartnerUsername() {
        return partnerUsername;
    }

    public void setPartnerUsername(String partnerUsername) {
        this.partnerUsername = partnerUsername;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @JsonProperty("last_updated_at")
    private Timestamp lastUpdatedAt;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("last_updated_by")
    private String lastUpdatedBy;

    public String getPartnerReg() {
        return partnerReg;
    }

    public void setPartnerReg(String partnerReg) {
        this.partnerReg = partnerReg;
    }
}
