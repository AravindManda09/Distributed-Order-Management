# Current Features

## Database Versioning

Implemented using Flyway migrations.

Current migrations:

* V1 - Products Table
* V2 - Inventory Table
* V3 - Orders & Order Items Tables
* V4 - Unique Constraint on Inventory Product
* V5 - Outbox Events Table

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
* PlaceOrderRequest DTO

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
Validate Product
      ↓
Reserve Inventory
      ↓
Calculate Total
      ↓
Create Order
      ↓
Create Order Item
      ↓
Create Outbox Event
```

---

## Event-Driven Architecture

Implemented:

* Transactional Outbox Pattern
* Outbox Event Persistence
* Scheduled Outbox Relay
* Apache Kafka Integration
* Kafka Producer
* JSON Event Publishing

Workflow:

```text
Order Created
      ↓
Outbox Event Stored
      ↓
Database Transaction Commits
      ↓
Outbox Scheduler
      ↓
Kafka Topic (order-created)
```

---

## Infrastructure

Implemented:

* Docker Compose
* PostgreSQL 16 (Docker)
* Apache Kafka
* Kafka UI
* Flyway Database Migrations

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
outbox_events
flyway_schema_history
```

Planned Tables:

```text
payments
shipments
returns
customers
```

---

# Roadmap

### Core Backend

* [x] Project Setup
* [x] PostgreSQL Integration
* [x] Flyway Integration
* [x] Docker Integration
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

### Distributed Systems

* [x] Transactional Outbox Pattern
* [x] Kafka Integration
* [x] Event Publishing
* [ ] Kafka Consumers
* [ ] Saga Pattern
* [ ] Redis Caching

### Payments

* [ ] Payment Processing
* [ ] Payment Consumer
* [ ] Refund Handling

### Shipments

* [ ] Shipment Creation
* [ ] Shipment Tracking

### Security

* [ ] Spring Security
* [ ] JWT Authentication
* [ ] Role-Based Authorization

### Deployment

* [ ] CI/CD Pipeline

---

# Status

🚧 Active Development

Implemented Modules:

* Catalog
* Inventory
* Order
* Shared Infrastructure

Current Capability:

```text
Customer Places Order
        ↓
Inventory Reserved
        ↓
Order Created
        ↓
Order Item Persisted
        ↓
Outbox Event Created
        ↓
Kafka Event Published
```

Next Milestone:

* Payment Module
* Kafka Consumer
* Event-Driven Payment Processing