package com.korike.logistics.service;

import com.korike.logistics.entity.Partner;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface LocationService {

    public JSONObject getDistanceGivenSrcDst(String srcLatLong, String dstLatLong) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, JSONException;
    public JSONObject getGeocodedLocation(String latLong);
    public JSONObject getRouteGivenSrcDst(String srcLatLong, String dstLatLong);
    public List<Partner> getNearestPartnersGivenRadius(String latLong, Integer radius);

}
