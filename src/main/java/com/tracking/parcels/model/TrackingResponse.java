package com.tracking.parcels.model;

import java.time.ZonedDateTime;

public class TrackingResponse {

    private String tracking_number;
    private ZonedDateTime created_at;

    public TrackingResponse() {
    }

    public TrackingResponse(String tracking_number, ZonedDateTime created_at) {
        this.tracking_number = tracking_number;
        this.created_at = created_at;
    }

    public String getTracking_number() {
        return tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }

    public ZonedDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(ZonedDateTime created_at) {
        this.created_at = created_at;
    }
}
