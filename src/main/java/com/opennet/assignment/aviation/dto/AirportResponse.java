package com.opennet.assignment.aviation.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirportResponse {

    @JsonProperty("facility_name")
    private String facilityName;

    private String city;

    private Map<String, Object> otherFields = new HashMap<>();

    @JsonAnySetter
    public void add(String key, Object value) {
        otherFields.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getOtherFields() {
        return otherFields;
    }
}