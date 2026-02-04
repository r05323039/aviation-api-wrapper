package com.opennet.assignment.aviation.controller;

import com.opennet.assignment.aviation.dto.AirportResponse;
import com.opennet.assignment.aviation.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @GetMapping("/{icao}")
    public List<AirportResponse> getAirport(@PathVariable String icao) {
        return airportService.getAirportDetails(icao.toUpperCase());
    }
}