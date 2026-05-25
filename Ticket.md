# Build a car park management API

- Vehicle allocation
- Parking availability
- Vehicle billing
- Vehicle exit handling

---
- If you are able to do so, you should consider the case where there are multiple clients sending HTTP request at the same time:

The in-memory repository can be protected using synchronized blocks so that updates to parking state are performed safely.
This helps prevent race conditions such as two vehicles being allocated the same parking space.

---

## Parking Status Endpoint

Implement:

```text
GET /parking
```

Requirements:

- Return available spaces
- Return occupied spaces

---

## Vehicle Allocation Endpoint

Implement:

```text
POST /parking
```

Requirements:

- Accept vehicle registration
- Accept vehicle type (1, 2, 3)
- Allocate first available parking space
- Return vehicle registration and entry time

Validation:

- Invalid vehicle type
- Duplicate vehicle
- No spaces available

---

## Vehicle Billing Endpoint

Implement:

```text
POST /parking/bill
```

Requirements:

- Find parked vehicle
- Calculate parking charge
- Apply vehicle charging rules
- Apply additional £1 every 5 minutes
- Release parking space
- Return billing information

Validation:

- Vehicle not found

---

## Testing

Validate:

- Vehicle allocation
- Space availability updates
- Billing calculations
- Vehicle exit flow
- Error handling

---

## Documentation

Provide:

- Setup instructions
- API examples
- Testing examples

---

## Questions / Clarifications

### Parking Capacity

Questions:

- What is the total parking capacity?

Decision:

- Capacity configured to 20 spaces.

---

### Additional Charging

Question:

Should additional charging use completed 5-minute intervals?

Decision:

Completed intervals used:

```text
4 mins = £0
5 mins = £1
9 mins = £1
10 mins = £2
```

---

### Vehicle Registration

Questions:

- Should duplicate registrations be rejected?
- Should duplicate attempts be logged?

Decision:

Duplicate active registrations rejected.

---

### Vehicle Exit

Questions:

- Does billing imply payment completed?
- Should payment be a separate endpoint?

Decision:

Billing endpoint represents vehicle exit completion.

Vehicle removed from active parking immediately after billing.

---

### Registration Validation

Questions:

- Should registration format be validated?
- Should registration casing be normalised?

Decision:

Registration stored exactly as provided.