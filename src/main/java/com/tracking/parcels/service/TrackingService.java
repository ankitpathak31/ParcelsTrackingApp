package com.tracking.parcels.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class TrackingService {

    private final Set<String> existingNumbers = ConcurrentHashMap.newKeySet();

    private static final String TRACKING_REGEX = "^[A-Z0-9]{1,16}$";

    public String generateTrackingNumber(String origin, String destination, BigDecimal weight, String createdAt,
                                         String customerId, String customerName, String slug) {
        if (!origin.matches("^[A-Z]{2}$")) {
            log.error("origin_country_id must be a 2-letter uppercase ISO 3166-1 alpha-2 code.");
            throw new IllegalArgumentException("origin_country_id must be a 2-letter uppercase ISO 3166-1 alpha-2 code.");
        }

        if (!destination.matches("^[A-Z]{2}$")) {
            log.error("destination_country_id must be a 2-letter uppercase ISO 3166-1 alpha-2 code.");
            throw new IllegalArgumentException("destination_country_id must be a 2-letter uppercase ISO 3166-1 alpha-2 code.");
        }

        ZonedDateTime createdAtTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
            createdAtTime = ZonedDateTime.parse(createdAt, formatter);
        } catch (DateTimeParseException e) {
            log.error("Invalid created_at format. Expected format: yyyy-MM-dd'T'HH:mm:ssXXX");
            throw new IllegalArgumentException("Invalid created_at format. Expected format: yyyy-MM-dd'T'HH:mm:ssXXX", e);
        }

        String prefix = (origin + destination).toUpperCase();
        String timePart = DateTimeFormatter.ofPattern("yyMM").format(createdAtTime);
        String trackingNumber;

        int maxAttempts = 10;
        int attempt = 0;

        do {
            if (attempt++ >= maxAttempts) {
                log.error("Failed to generate a unique tracking number after multiple attempts");
                throw new IllegalStateException("Failed to generate a unique tracking number after multiple attempts");
            }

            String uniquePart = UUID.randomUUID()
                    .toString()
                    .replaceAll("-", "")
                    .substring(0, 8)
                    .toUpperCase();

            trackingNumber = (prefix + timePart + uniquePart).substring(0, 16);

        } while (!existingNumbers.add(trackingNumber) || !trackingNumber.matches(TRACKING_REGEX));

        return trackingNumber;
    }
}
