package com.opennet.assignment.aviation.service;

import com.opennet.assignment.aviation.client.AviationClient;
import com.opennet.assignment.aviation.dto.AirportResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirportService {

    private final AviationClient aviationClient;

    @Retry(name = "aviationService")
    @CircuitBreaker(name = "aviationService", fallbackMethod = "getAirportFallback")
    public List<AirportResponse> getAirportDetails(String icao) {
        log.info("Fetching details for: {}", icao);

        Map<String, List<AirportResponse>> responseMap = aviationClient.getAirports(icao);

        if (responseMap != null && responseMap.containsKey(icao)) {
            return responseMap.get(icao);
        }

        return responseMap != null && !responseMap.isEmpty()
                ? responseMap.values().stream().findFirst().orElse(List.of())
                : List.of();
    }

    // 當熔斷開啟或 API 噴錯時執行
    public List<AirportResponse> getAirportFallback(String icao, Throwable t) {
        log.warn("Fallback triggered: {}", t.getMessage());
        return List.of();
    }
}