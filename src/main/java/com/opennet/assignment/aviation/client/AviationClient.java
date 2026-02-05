package com.opennet.assignment.aviation.client;

import com.opennet.assignment.aviation.dto.AirportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "aviationClient", url = "${aviation.api.url}")
public interface AviationClient {
    String APPLICATION_JSON = "Accept=application/json";
    @GetMapping(value = "/airports", headers = APPLICATION_JSON)
    Map<String, List<AirportResponse>> getAirports(@RequestParam("apt") String icao);

}