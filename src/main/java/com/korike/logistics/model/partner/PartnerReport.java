package com.korike.logistics.model.partner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerReport {

    @JsonProperty("order_list")
    private List<PartnerOrderObject> order_list;

    @JsonProperty("max_order_time")
    private String max_time;

    @JsonProperty("min_order_time")
    private String min_time;

    @JsonProperty("average_order_time")
    private String average_order_time;

    @JsonProperty("max_order_amount")
    private double max_order_amount;

    @JsonProperty("min_order_amount")
    private double min_order_amount;

    @JsonProperty("order_counts_am")
    private double order_counts_am;

    @JsonProperty("order_counts_pm")
    private double order_counts_pm;

    @JsonProperty("average_order_amount")
    private double average_order_amount;

    @JsonProperty("count_wholesale")
    private double count_wholesale;

    @JsonProperty("count_retail")
    private double count_retail;

    @JsonProperty("pincode_order_counts")
    private Map<String, Integer> pincode_order_counts;

    @JsonProperty("total_order_count")
    private int total_order_count;

    @JsonProperty("cancelled_order_count")
    private int cancelled_order_count;

    @JsonProperty("completed_order_count")
    private int completed_order_count;

    @JsonProperty("inprogress_order_count")
    private int inprogress_order_count;

    public List<PartnerOrderObject> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<PartnerOrderObject> order_list) {
        this.order_list = order_list;
    }

    public String getMax_time() {
        return max_time;
    }

    public void setMax_time(String max_time) {
        this.max_time = max_time;
    }

    public String getMin_time() {
        return min_time;
    }

    public void setMin_time(String min_time) {
        this.min_time = min_time;
    }

    public String getAverage_order_time() {
        return average_order_time;
    }

    public void setAverage_order_time(String average_order_time) {
        this.average_order_time = average_order_time;
    }

    public double getMax_order_amount() {
        return max_order_amount;
    }

    public void setMax_order_amount(double max_order_amount) {
        this.max_order_amount = max_order_amount;
    }

    public double getMin_order_amount() {
        return min_order_amount;
    }

    public void setMin_order_amount(double min_order_amount) {
        this.min_order_amount = min_order_amount;
    }

    public double getOrder_counts_am() {
        return order_counts_am;
    }

    public void setOrder_counts_am(double order_counts_am) {
        this.order_counts_am = order_counts_am;
    }

    public double getOrder_counts_pm() {
        return order_counts_pm;
    }

    public void setOrder_counts_pm(double order_counts_pm) {
        this.order_counts_pm = order_counts_pm;
    }

    public double getAverage_order_amount() {
        return average_order_amount;
    }

    public void setAverage_order_amount(double average_order_amount) {
        this.average_order_amount = average_order_amount;
    }

    public double getCount_wholesale() {
        return count_wholesale;
    }

    public void setCount_wholesale(double count_wholesale) {
        this.count_wholesale = count_wholesale;
    }

    public double getCount_retail() {
        return count_retail;
    }

    public void setCount_retail(double count_retail) {
        this.count_retail = count_retail;
    }

    public Map<String, Integer> getPincode_order_counts() {
        return pincode_order_counts;
    }

    public void setPincode_order_counts(Map<String, Integer> pincode_order_counts) {
        this.pincode_order_counts = pincode_order_counts;
    }

    public int getTotal_order_count() {
        return total_order_count;
    }

    public void setTotal_order_count(int total_order_count) {
        this.total_order_count = total_order_count;
    }

    public int getCancelled_order_count() {
        return cancelled_order_count;
    }

    public void setCancelled_order_count(int cancelled_order_count) {
        this.cancelled_order_count = cancelled_order_count;
    }

    public int getCompleted_order_count() {
        return completed_order_count;
    }

    public void setCompleted_order_count(int completed_order_count) {
        this.completed_order_count = completed_order_count;
    }

    public int getInprogress_order_count() {
        return inprogress_order_count;
    }

    public void setInprogress_order_count(int inprogress_order_count) {
        this.inprogress_order_count = inprogress_order_count;
    }
}
