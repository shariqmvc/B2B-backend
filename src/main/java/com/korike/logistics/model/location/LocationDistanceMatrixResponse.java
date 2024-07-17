package com.korike.logistics.model.location;

import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationDistanceMatrixResponse{
    public ArrayList<String> destination_addresses;
    public ArrayList<String> origin_addresses;
    public ArrayList<Row> rows;
    public String status;

    public ArrayList<String> getDestination_addresses() {
        return destination_addresses;
    }

    public void setDestination_addresses(ArrayList<String> destination_addresses) {
        this.destination_addresses = destination_addresses;
    }

    public ArrayList<String> getOrigin_addresses() {
        return origin_addresses;
    }

    public void setOrigin_addresses(ArrayList<String> origin_addresses) {
        this.origin_addresses = origin_addresses;
    }

    public ArrayList<Row> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Row> rows) {
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}