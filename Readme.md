# Car Park Management API

## Overview

This project is a Play Framework Scala API that provides an in-memory car park management system.

The API supports:

- Allocating vehicles to parking spaces
- Viewing available and occupied spaces
- Calculating parking charges
- Releasing parking spaces on vehicle exit
- Vehicle validation
- Basic error handling

Parking information is stored entirely in memory.

---

## Vehicle Charging Rules

| Vehicle Type | ID | Cost Per Minute |
|---------------|----|-----------------|
| Small Car | 1 | £0.10 |
| Medium Car | 2 | £0.20 |
| Large Car | 3 | £0.40 |

Additional charging:

```text
Every completed 5 minutes parked adds £1
```

Examples:

| Time Parked | Small Car | Additional Charge | Total |
|--------------|------------|-------------------|-------|
| 5 mins | £0.50 | £1 | £1.50 |
| 10 mins | £1.00 | £2 | £3.00 |
| 15 mins | £1.50 | £3 | £4.50 |

---

## API Endpoints

### GET /parking

Returns available and occupied parking spaces.

Request:

```http
GET /parking
```

Response:

```json
{
  "availableSpaces": 20,
  "occupiedSpaces": 0
}
```

---

### POST /parking

Parks a vehicle in the first available parking space.

Request:

```http
POST /parking
```

Body:

```json
{
  "vehicleReg": "ABC123",
  "vehicleType": 1
}
```

Response:

```json
{
  "vehicleReg": "ABC123",
  "timeIn": "2026-05-22T10:00:00Z"
}
```

---

### POST /parking/bill

Calculates parking charges and releases the parking space.

Request:

```http
POST /parking/bill
```

Body:

```json
{
  "vehicleReg": "ABC123"
}
```

Response:

```json
{
  "vehicleReg": "ABC123",
  "vehicleCharge": 3.00,
  "timeIn": "2026-05-22T10:00:00Z",
  "timeOut": "2026-05-22T10:10:00Z"
}
```

---

## Running Application

Start application:

```bash
sbt run
```

Application URL:

```text
http://localhost:9000
```
---

## Curl Examples

Get parking status:

```bash
curl http://localhost:9000/parking
```

Park vehicle:

```bash
curl -X POST http://localhost:9000/parking \
-H "Content-Type: application/json" \
-d '{"vehicleReg":"ABC123","vehicleType":1}'
```

Bill vehicle:

```bash
curl -X POST http://localhost:9000/parking/bill \
-H "Content-Type: application/json" \
-d '{"vehicleReg":"ABC123"}'
```

---

## Postman Testing

End points:

1. GET `/parking`
2. POST `/parking`
3. POST `/parking/bill`

---

## Running Tests

Run all automated tests:

```bash
sbt test
```
