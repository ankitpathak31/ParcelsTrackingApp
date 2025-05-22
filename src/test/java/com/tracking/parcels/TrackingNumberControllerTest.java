package com.tracking.parcels;

import com.tracking.parcels.controllers.TrackingNumberController;
import com.tracking.parcels.service.TrackingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

@WebMvcTest(TrackingNumberController.class)
public class TrackingNumberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackingService trackingService;

    @Test
    void testGetNextTrackingNumber_Positive() throws Exception {
        // Dummy input values
        String dummyTrackingNumber = "DUMMY123456";

        // Mock the TrackingService to return the dummy tracking number
        when(trackingService.generateTrackingNumber(
                "US", "UK", new BigDecimal("5.0"), "2024-05-20T10:00:00Z",
                "cust123", "Dummy Customer", "dummy-customer"))
                .thenReturn(dummyTrackingNumber);

        // Perform GET request and verify the response
        mockMvc.perform(get("/api/parcel-tracking/next-tracking-number")
                        .param("origin_country_id", "US")
                        .param("destination_country_id", "UK")
                        .param("weight", "5.0")
                        .param("created_at", "2024-05-20T10:00:00Z")
                        .param("customer_id", "cust123")
                        .param("customer_name", "Dummy Customer")
                        .param("customer_slug", "dummy-customer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // HTTP 200 OK
                .andExpect(jsonPath("$.tracking_number").value(dummyTrackingNumber))  // Check tracking number
                .andExpect(jsonPath("$.created_at").exists());  // Check if created_at exists
    }

    @Test
    void testGetNextTrackingNumber_NoTrackingNumber() throws Exception {
        // Test case where the service returns an empty tracking number
        when(trackingService.generateTrackingNumber(
                "US", "UK", new BigDecimal("5.0"), "2024-05-20T10:00:00Z",
                "cust123", "Dummy Customer", "dummy-customer"))
                .thenReturn("");

        // Perform GET request and verify the response with empty tracking number
        mockMvc.perform(get("/api/parcel-tracking/next-tracking-number")
                        .param("origin_country_id", "US")
                        .param("destination_country_id", "UK")
                        .param("weight", "5.0")
                        .param("created_at", "2024-05-20T10:00:00Z")
                        .param("customer_id", "cust123")
                        .param("customer_name", "Dummy Customer")
                        .param("customer_slug", "dummy-customer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // HTTP 200 OK
                .andExpect(jsonPath("$.tracking_number").value(""))  // Expect empty tracking number
                .andExpect(jsonPath("$.created_at").exists());  // Ensure created_at exists
    }
}
