package com.korike.logistics.model.partner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerOrderObject {

    @JsonProperty("order_id")
    private String order_id;

    @JsonProperty("order_status")
    private String order_status;

    @JsonProperty("order_time_taken")
    private String order_time_taken;

    @JsonProperty("is_order_am")
    private Boolean is_order_am;

    @JsonProperty("order_location")
    private String order_location;

    @JsonProperty("order_invoice_amount")
    private double order_invoice_amount;

    @JsonProperty("order_payment_method")
    private String order_payment_method;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_time_taken() {
        return order_time_taken;
    }

    public void setOrder_time_taken(String order_time_taken) {
        this.order_time_taken = order_time_taken;
    }

    public String getOrder_location() {
        return order_location;
    }

    public void setOrder_location(String order_location) {
        this.order_location = order_location;
    }

    public double getOrder_invoice_amount() {
        return order_invoice_amount;
    }

    public void setOrder_invoice_amount(double order_invoice_amount) {
        this.order_invoice_amount = order_invoice_amount;
    }

    public String getOrder_payment_method() {
        return order_payment_method;
    }

    public void setOrder_payment_method(String order_payment_method) {
        this.order_payment_method = order_payment_method;
    }

    public Boolean getIs_order_am() {
        return is_order_am;
    }

    public void setIs_order_am(Boolean is_order_am) {
        this.is_order_am = is_order_am;
    }

}
