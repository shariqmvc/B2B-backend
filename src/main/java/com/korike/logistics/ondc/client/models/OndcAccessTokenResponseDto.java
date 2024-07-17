package com.korike.logistics.ondc.client.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OndcAccessTokenResponseDto {

    @JsonProperty("accessToken")
    private String accessToken;

}
