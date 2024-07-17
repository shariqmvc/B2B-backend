package com.korike.logistics.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.GeoApiContext;
import com.korike.logistics.common.HttpClientWorkflow;
import com.korike.logistics.entity.Partner;
import com.korike.logistics.model.location.LocationDistanceMatrixResponse;
import com.korike.logistics.service.LocationService;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private static Logger logger = Logger.getLogger(LocationServiceImpl.class);
    private static final String API_KEY = "AIzaSyDcNKrGpqIqmSnMzUjBKZkNIVacvRP6ZKY";


    @Override
    public JSONObject getDistanceGivenSrcDst(String srcLatLong, String dstLatLong) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, JSONException {
        String url="https://maps.googleapis.com/maps/api/distancematrix/json?origins="+srcLatLong+"&destinations="+dstLatLong+"&units=imperial&key="+ API_KEY;
        JSONObject response = HttpClientWorkflow.executeGetRequestsForRBody(url, null,null, "",false, "");
        return response;
    }

    @Override
    public JSONObject getGeocodedLocation(String latLong) {
        return null;
    }

    @Override
    public JSONObject getRouteGivenSrcDst(String srcLatLong, String dstLatLong) {
        return null;
    }

    @Override
    public List<Partner> getNearestPartnersGivenRadius(String latLong, Integer radius) {


        return null;
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, JSONException {
        LocationServiceImpl locationService = new LocationServiceImpl();
        JSONObject response = locationService.getDistanceGivenSrcDst("12.994444227282932,77.55395344011764", "12.914166585674014,77.4863639994775");
        ObjectMapper om = new ObjectMapper();
        LocationDistanceMatrixResponse distanceMatrixResponse = om.readValue(response.toString(), LocationDistanceMatrixResponse.class);
        System.out.println("DistanceMatrix Object "+distanceMatrixResponse.getRows().toString());
        System.out.println("Distance Text "+distanceMatrixResponse.getRows().get(0).getElements().get(0).getDistance().text);
        System.out.println("Distance Value "+distanceMatrixResponse.getRows().get(0).getElements().get(0).getDistance().value);
    }
}
