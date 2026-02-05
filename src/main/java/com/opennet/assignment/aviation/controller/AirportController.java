package com.opennet.assignment.aviation.controller;

import com.opennet.assignment.aviation.dto.AirportResponse;
import com.opennet.assignment.aviation.dto.ApiResponse;
import com.opennet.assignment.aviation.service.AirportService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/airports")
@RequiredArgsConstructor
@Validated
public class AirportController {

    private final AirportService airportService;

    @GetMapping("/{icao}")
    public ApiResponse<List<AirportResponse>> getAirport(@PathVariable
                                            @Pattern(regexp = "^[A-Z0-9]{4}$", message = "ICAO code must be exactly 4 alphanumeric characters")
                                            String icao) {
        List<AirportResponse> result = airportService.getAirportDetails(icao.toUpperCase());
        return ApiResponse.success(result);
    }
}