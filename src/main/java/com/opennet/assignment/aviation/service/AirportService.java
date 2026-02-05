package com.opennet.assignment.aviation.service;

import com.opennet.assignment.aviation.client.AviationClient;
import com.opennet.assignment.aviation.dto.AirportResponse;
import com.opennet.assignment.aviation.exception.BusinessException;
import com.opennet.assignment.aviation.exception.ErrorCode;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "airports", key = "#icao", unless = "#result == null || #result.isEmpty()")
    public List<AirportResponse> getAirportDetails(String icao) {
        log.info("Fetching details for: {}", icao);

        Map<String, List<AirportResponse>> responseMap = aviationClient.getAirports(icao);

        if (responseMap == null || responseMap.isEmpty()) {
            log.info("Upstream returned empty response for ICAO: {}", icao);
            return List.of();
        }
        if (!responseMap.containsKey(icao)) {
            log.warn("Upstream returned data but missing expected key: [{}]. Keys found: {}", icao, responseMap.keySet());
            return List.of();
        }

        return responseMap.get(icao);
    }

    public List<AirportResponse> getAirportFallback(String icao, Throwable t) {
        log.error("Fallback triggered for ICAO [{}]. Original cause: {}", icao, t.getMessage(), t);
        throw new BusinessException(ErrorCode.UPSTREAM_SERVICE_UNAVAILABLE);
    }
}