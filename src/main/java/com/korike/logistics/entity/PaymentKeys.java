package com.korike.logistics.entity;

import javax.persistence.*;

@Entity
@Table(name="payment_keys")
public class PaymentKeys {
    @Id
    @Column(name="id", length=100)
    private String id;

    @Column(name="key", nullable = false)
    private String key;

    @Column(name="salt", nullable = false, columnDefinition="TEXT")
    private String salt;

    @Column(name="payment_gateway", nullable = false)
    private String payment_gateway;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPayment_gateway() {
        return payment_gateway;
    }

    public void setPayment_gateway(String payment_gateway) {
        this.payment_gateway = payment_gateway;
    }
}
