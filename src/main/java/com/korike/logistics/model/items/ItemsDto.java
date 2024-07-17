package com.korike.logistics.model.items;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemsDto {

    @JsonProperty("item_details")
    private ItemsDetails itemsDetails;

}
