package com.korike.logistics.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="partner_declined_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate

public class PartnerDeclinedOrders {
    @Id
    @Column(name="decline_id", length=100, nullable = false)
    private String declineId;

    @Column(name="order_id", nullable = false)
    private String orderId;

    @Column(name="partner_id", nullable = false)
    private String partnerId;

    @Column(name="partner_location", nullable = true)
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

}

