package com.opennet.assignment.aviation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirportResponse {
    private String icao;
    private String iata;
    private String name;
    private String city;
    private String state;
    private String country;
    private String elevation;
    private String lat;
    private String lon;
    private String tz;
}
