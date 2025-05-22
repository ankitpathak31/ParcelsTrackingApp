# Parcel Tracking Number Generator API

This Spring Boot project provides an API endpoint to generate a new tracking number for parcel shipments based on origin, destination, parcel weight, and customer details.

## 📦 Features

- Generate unique tracking numbers
- Accepts comprehensive parcel and customer information
- Returns tracking number with timestamp

## 🛠 Tech Stack

- Java 8+
- Spring Boot
- Maven
- REST API

---

## 🔗 API Endpoint

### `GET /api/parcel-tracking/next-tracking-number`

Generates the next available tracking number for a parcel shipment.

### Request Parameters

| Parameter             | Type         | Description                              |
|----------------------|--------------|------------------------------------------|
| `origin_country_id`  | `String`     | Origin country code (e.g., `MY`)         |
| `destination_country_id` | `String`     | Destination country code (e.g., `ID`)    |
| `weight`             | `BigDecimal` | Parcel weight in kilograms (e.g., `1.234`) |
| `created_at`         | `String`     | ISO 8601 date-time format (e.g., `2018-11-20T19:29:32+08:00`) |
| `customer_id`       | `String`    | The he customer’s UUID (e.g.,`de619854-b59b-425e-9db4-943979e1bd49`).

Ex.  http://localhost:8080/api/parcel-tracking/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2018-11-20T19:29:32%2B08:00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics