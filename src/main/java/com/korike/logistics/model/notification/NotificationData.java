package com.korike.logistics.model.notification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationData {

    @JsonProperty("notification_data")
    private Map<String, String> data;
}
