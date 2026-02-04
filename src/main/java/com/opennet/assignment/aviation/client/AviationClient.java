package com.opennet.assignment.aviation.client;

import com.opennet.assignment.aviation.dto.AirportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "aviationClient", url = "https://api.aviationapi.com/v1")
public interface AviationClient {

    @GetMapping(value = "/airports", headers = "Accept=application/json")
    Map<String, List<AirportResponse>> getAirports(@RequestParam("apt") String icao);
}