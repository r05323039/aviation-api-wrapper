package com.opennet.assignment;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
class AirportIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        Objects.requireNonNull(cacheManager.getCache("airports")).clear();
        WireMock.reset();
    }

    @Test
    void testGetAirport_Success() throws Exception {
        String jsonBody = """
                {
                  "KLAX": [
                    {
                      "facility_name": "LAX"
                    }
                  ]
                }
                """;

        stubFor(WireMock.get(urlPathMatching("/v1/airports"))
                .withQueryParam("apt", matching("KLAX"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonBody)));

        mockMvc.perform(get("/api/v1/airports/KLAX"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].facility_name").value("LAX"));
    }

    @Test
    void testGetAirport_Timeout_Fallback() throws Exception {
        stubFor(WireMock.get(urlPathMatching("/v1/airports"))
                .withQueryParam("apt", matching("KLAX"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withFixedDelay(3000)));

        mockMvc.perform(get("/api/v1/airports/KLAX"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.code").value(503));
    }@Test
    void testGetAirport_CacheHit() throws Exception {
        stubFor(WireMock.get(urlPathMatching("/v1/airports"))
                .withQueryParam("apt", matching("KLAX"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                              "KLAX": [
                                { "facility_name": "KLAX" }
                              ]
                            }
                            """)));

        mockMvc.perform(get("/api/v1/airports/KLAX"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/airports/KLAX"))
                .andExpect(status().isOk());

        verify(1, getRequestedFor(urlPathMatching("/v1/airports"))
                .withQueryParam("apt", matching("KLAX")));
    }
}