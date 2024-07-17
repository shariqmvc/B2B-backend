package com.korike.logistics.entity;

import com.korike.logistics.model.payments.PaymentMode;
import com.korike.logistics.model.payments.PaymentStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="payment_information")
public class PaymentInformation {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "email", nullable = true)
    private String email;
    @Column(name = "name", nullable = true)
    private String name;
    @Column(name = "phone", nullable = true)
    private String phone;
    @Column(name = "product_info", nullable = true)
    private String productInfo;
    @Column(name = "amount", nullable = true)
    private Double amount;
    @Column(name = "payment_status", nullable = true)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Column(name = "payment_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    @Column(name = "txn_id", nullable = true)
    private String txnId;
    @Column(name = "mihpay_id", nullable = true)
    private String mihpayId;
    @Column(name = "hash", nullable = true, columnDefinition="TEXT")
    private String hash;

    @Column(name = "mode", nullable = true)
    @Enumerated(EnumType.STRING)
    private PaymentMode mode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getMihpayId() {
        return mihpayId;
    }

    public void setMihpayId(String mihpayId) {
        this.mihpayId = mihpayId;
    }

    public PaymentMode getMode() {
        return mode;
    }

    public void setMode(PaymentMode mode) {
        this.mode = mode;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
