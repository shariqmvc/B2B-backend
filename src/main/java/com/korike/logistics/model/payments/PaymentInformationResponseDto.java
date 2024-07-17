package com.korike.logistics.model.payments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.korike.logistics.model.orders.OrderItemsListDto;
import org.codehaus.jettison.json.JSONObject;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentInformationResponseDto {

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("product_info")
    private List<OrderItemsListDto> product_info;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("payment_status")
    private String payment_status;

    @JsonProperty("txn_id")
    private String txn_id;

    @JsonProperty("mihpay_id")
    private String mihpay_id;

    @JsonProperty("hash")
    private PaymentHashes hash;

    @JsonProperty("mode")
    private String mode;

    @JsonProperty("key")
    private String key;

    @JsonProperty("salt")
    private String salt;

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

    public List<OrderItemsListDto> getProduct_info() {
        return product_info;
    }

    public void setProduct_info(List<OrderItemsListDto> product_info) {
        this.product_info = product_info;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public String getMihpay_id() {
        return mihpay_id;
    }

    public void setMihpay_id(String mihpay_id) {
        this.mihpay_id = mihpay_id;
    }

    public PaymentHashes getHash() {
        return hash;
    }

    public void setHash(PaymentHashes hash) {
        this.hash = hash;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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
}
