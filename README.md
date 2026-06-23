# Current Features

## Database Versioning

Implemented using Flyway migrations.

Current migrations:

* V1 - Products Table
* V2 - Inventory Table
* V3 - Orders & Order Items Tables
* V4 - Unique Constraint on Inventory Product

---

## Catalog Module

Implemented:

* Product Entity
* Product Repository
* Product Service
* Product Controller

Endpoints:

```http
POST /products

GET /products

GET /products/{id}
```

---

## Inventory Module

Implemented:

* InventoryItem Entity
* InventoryRepository
* InventoryService
* InventoryController

Features:

* Inventory Creation
* Inventory Retrieval
* Stock Reservation
* Stock Release
* Availability Validation
* Duplicate Inventory Prevention

Endpoints:

```http
POST /inventory

GET /inventory/{productId}

POST /inventory/{productId}/reserve?qty={quantity}

POST /inventory/{productId}/release?qty={quantity}
```

---

## Order Module

Implemented:

* Order Entity
* OrderItem Entity
* OrderStatus Enum
* OrderRepository
* OrderItemRepository
* OrderService
* OrderController

Features:

* Order Placement
* Order Item Persistence
* Total Amount Calculation
* Inventory Reservation During Order Placement
* Transactional Order Workflow

Endpoints:

```http
POST /orders
```

Example Request:

```json
{
  "productId": 1,
  "quantity": 2
}
```

Workflow:

```text
Place Order
      ↓
Fetch Product
      ↓
Reserve Inventory
      ↓
Calculate Total
      ↓
Create Order
      ↓
Create Order Item
```

---

## Exception Handling

Implemented:

* ResourceNotFoundException
* InsufficientStockException
* GlobalExceptionHandler

HTTP Status Mapping:

* 404 Not Found
* 409 Conflict

Provides centralized exception handling across the application.

---

# Database

Current Tables:

```text
products
inventory_items
orders
order_items
flyway_schema_history
```

Planned Tables:

```text
payments
shipments
returns
outbox_events
customers
```

---

# Roadmap

### Core Backend

* [x] Project Setup
* [x] PostgreSQL Integration
* [x] Flyway Integration
* [x] Catalog Module
* [x] Inventory Module
* [x] Order Module
* [x] Global Exception Handling

### Inventory

* [x] Inventory Management
* [x] Stock Reservation
* [x] Stock Release
* [x] Duplicate Inventory Prevention

### Orders

* [x] Order Placement
* [ ] Order Tracking
* [ ] Order Cancellation

### Payments

* [ ] Payment Processing
* [ ] Refund Handling

### Shipments

* [ ] Shipment Creation
* [ ] Shipment Tracking

### Distributed Systems

* [ ] Transactional Outbox
* [ ] Kafka Integration
* [ ] Saga Pattern
* [ ] Redis Caching

### Security

* [ ] Spring Security
* [ ] JWT Authentication
* [ ] Role-Based Authorization

### Deployment

* [ ] Dockerization
* [ ] CI/CD Pipeline

---

# Status

🚧 Active Development

Implemented Modules:

* Catalog
* Inventory
* Order

Current Capability:

```text
Customer Places Order
        ↓
Inventory Reserved
        ↓
Order Created
        ↓
Order Item Persisted
```

Next Milestone:

* Transactional Outbox Pattern
* Domain Events
* Kafka Integration
