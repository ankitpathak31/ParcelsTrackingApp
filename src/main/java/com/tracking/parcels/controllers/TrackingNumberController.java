package com.tracking.parcels.controllers;

import com.tracking.parcels.model.TrackingResponse;
import com.tracking.parcels.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/parcel-tracking")
public class TrackingNumberController {


    @Autowired
    private TrackingService trackingService;

    @GetMapping("/next-tracking-number")
    public ResponseEntity<TrackingResponse> getNextTrackingNumber(
            @RequestParam String origin_country_id,
            @RequestParam String destination_country_id,
            @RequestParam BigDecimal weight,
            @RequestParam String created_at,
            @RequestParam String customer_id,
            @RequestParam String customer_name,
            @RequestParam String customer_slug
    ) {
        String trackingNumber = trackingService.generateTrackingNumber(
                origin_country_id, destination_country_id, weight, created_at,
                customer_id, customer_name, customer_slug
        );

        TrackingResponse response = new TrackingResponse(trackingNumber, ZonedDateTime.now());
        return ResponseEntity.ok(response);
    }
}
