# Current Features

## Database Versioning

Implemented using Flyway migrations.

Current migrations:

* V1 - Products Table
* V2 - Inventory Table
* V3 - Orders & Order Items Tables
* V4 - Unique Constraint on Inventory Product
* V5 - Outbox Events Table
* V6 - Payments Table

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
* InventoryConsumer
* InventoryController

Features:

* Inventory Creation
* Inventory Retrieval
* Stock Reservation
* Stock Release
* Duplicate Inventory Prevention
* Inventory Compensation on Order Cancellation

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
* OrderEventConsumer
* PlaceOrderRequest DTO

Features:

* Order Placement
* Order Confirmation
* Order Cancellation (Payment Failure)
* Order Item Persistence
* Inventory Reservation
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

---

## Payment Module

Implemented:

* Payment Entity
* PaymentRepository
* PaymentService
* PaymentConsumer
* PaymentStatus Enum

Features:

* Event-Driven Payment Processing
* Random Payment Success / Failure Simulation
* Payment Event Publishing

Workflow:

```text
ORDER_CREATED
      ↓
Payment Consumer
      ↓
Process Payment
      ↓
PAYMENT_COMPLETED
```

---

## Event-Driven Architecture

Implemented:

* Transactional Outbox Pattern
* Event Publisher Abstraction
* Scheduled Outbox Relay
* Apache Kafka Integration
* Kafka Producer
* Kafka Consumers
* JSON Event Serialization
* Domain Events

Current Events:

* ORDER_CREATED
* PAYMENT_COMPLETED
* ORDER_CANCELLED

Kafka Topics:

* order-created
* payment-events
* order-events

---

## Saga Pattern (Choreography)

Implemented:

* Payment Success Workflow
* Payment Failure Compensation
* Event-Driven Inventory Compensation

Success Flow:

```text
Customer Places Order
        ↓
Reserve Inventory
        ↓
ORDER_CREATED
        ↓
Payment Processed
        ↓
PAYMENT_COMPLETED (SUCCESS)
        ↓
Order Confirmed
```

Failure Flow:

```text
Customer Places Order
        ↓
Reserve Inventory
        ↓
ORDER_CREATED
        ↓
Payment Processed
        ↓
PAYMENT_COMPLETED (FAILED)
        ↓
Order Cancelled
        ↓
ORDER_CANCELLED
        ↓
Inventory Released
```

---

## Infrastructure

Implemented:

* Docker Compose
* PostgreSQL
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

---

# Database

Current Tables

```text
products
inventory_items
orders
order_items
payments
outbox_events
flyway_schema_history
```

---

# Architecture

```text
Client
   │
   ▼
Order Module
   │
   ▼
Transactional Outbox
   │
   ▼
Outbox Scheduler
   │
   ▼
Kafka
   │
   ├──────────────► Payment Consumer
   │                    │
   │                    ▼
   │             Payment Service
   │                    │
   │                    ▼
   │           PAYMENT_COMPLETED
   │                    │
   ▼                    ▼
Order Consumer ◄────────┘
   │
   ├──────────── Success ───────────► Confirm Order
   │
   └──────────── Failure ───────────► Cancel Order
                                         │
                                         ▼
                                 ORDER_CANCELLED
                                         │
                                         ▼
                                 Inventory Consumer
                                         │
                                         ▼
                                 Release Inventory
```

---

# Roadmap

## Core Backend

- [x] Project Setup
- [x] PostgreSQL Integration
- [x] Flyway Integration
- [x] Docker Integration
- [x] Catalog Module
- [x] Inventory Module
- [x] Order Module
- [x] Payment Module
- [x] Global Exception Handling

## Distributed Systems

- [x] Transactional Outbox Pattern
- [x] Kafka Integration
- [x] Kafka Producers
- [x] Kafka Consumers
- [x] Event Publishing
- [x] Event-Driven Communication
- [x] Saga Pattern (Choreography)
- [x] Eventual Consistency

## Payments

- [x] Payment Processing
- [x] Payment Consumer
- [ ] Refund Handling

## Shipments

- [ ] Shipment Creation
- [ ] Shipment Tracking

## Returns

- [ ] Return Processing

## Performance

- [ ] Redis Caching

## Security

- [ ] Spring Security
- [ ] JWT Authentication
- [ ] Role-Based Authorization

## Deployment

- [ ] CI/CD Pipeline

---

# Status

🚧 Active Development

Implemented Modules:

* Catalog
* Inventory
* Order
* Payment
* Shared Infrastructure

Current Capability

```text
Customer Places Order
        ↓
Reserve Inventory
        ↓
Create Order
        ↓
Publish ORDER_CREATED
        ↓
Kafka
        ↓
Payment Processing
        ↓
Publish PAYMENT_COMPLETED
        ↓
Confirm / Cancel Order
        ↓
Publish ORDER_CANCELLED (Failure Only)
        ↓
Release Inventory
```

Next Milestone

* Shipment Module
* Shipment Events
* Shipment Consumer
* Order Tracking