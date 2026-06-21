# Distributed Order Management System (OMS)

A scalable Order Management System inspired by modern e-commerce platforms such as Amazon.

This project is being developed as a **Modular Monolith** with clearly defined module boundaries so that each module can later be extracted into an independent microservice with minimal changes.

The primary goal of this project is to explore real-world backend architecture patterns including inventory reservation, order processing, event-driven communication, distributed transactions, caching, and eventual consistency.

---

# Architecture

Current Architecture Style:

**Modular Monolith**

```text
com.oms

├── catalog
├── inventory
├── order
├── payment
├── shipment
├── returns
└── shared
```

Future Evolution:

```text
Catalog Module    → Catalog Service
Inventory Module → Inventory Service
Order Module     → Order Service
Payment Module   → Payment Service
Shipment Module  → Shipment Service
Returns Module   → Returns Service
```

Module boundaries are intentionally designed to support future migration to a microservices architecture.

---

# Technology Stack

## Backend

* Java 21
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Flyway
* Maven

## Planned Technologies

* Apache Kafka
* Redis
* Docker
* Spring Security + JWT
* React + TypeScript
* Spring Modulith

---

# Modules

## Catalog

Responsible for product management.

Features:

* Product creation
* Product retrieval
* Product listing

---

## Inventory

Responsible for stock management.

Planned Features:

* Inventory tracking
* Stock reservation
* Stock release
* Availability checks

---

## Order

Responsible for order lifecycle management.

Planned Features:

* Place order
* Order tracking
* Order cancellation

---

## Payment

Responsible for payment processing.

Planned Features:

* Payment initiation
* Payment confirmation
* Payment failure handling
* Refund processing

---

## Shipment

Responsible for shipment lifecycle.

Planned Features:

* Shipment creation
* Shipment tracking
* Delivery updates

---

## Returns

Responsible for return and refund workflows.

Planned Features:

* Return requests
* Return approval
* Inventory restocking
* Refund initiation

---

# Current Features

## Database Versioning

Implemented using Flyway migrations.

Current migration:

```sql
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    price DECIMAL(10,2)
);
```

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

## Exception Handling

Implemented:

* ResourceNotFoundException
* GlobalExceptionHandler

Provides centralized exception handling across the application.

---

# Planned Distributed Systems Features

## Transactional Outbox Pattern

Ensures reliable event publication without dual-write issues.

---

## Event-Driven Communication

Kafka-based communication between modules.

Example Flow:

```text
OrderPlaced
      ↓
PaymentProcessed
      ↓
OrderConfirmed
      ↓
ShipmentCreated
```

---

## Saga Pattern

Distributed transaction management using compensating actions.

Example:

```text
Reserve Inventory
        ↓
Process Payment
        ↓
Payment Failed
        ↓
Release Inventory
        ↓
Cancel Order
```

---

## Redis Caching

Planned cache targets:

* Product Catalog
* Inventory Availability
* Order Status

---

# Database

Current Tables:

```text
products
flyway_schema_history
```

Planned Tables:

```text
inventory_items
orders
order_items
payments
shipments
returns
outbox_events
```

---

# Running Locally

## Clone Repository

```bash
git clone <repository-url>
```

## Start PostgreSQL

Create database:

```sql
CREATE DATABASE oms_db;
```

## Run Application

```bash
mvn clean install

mvn spring-boot:run
```

Application:

```text
http://localhost:8080
```

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

---

# Roadmap

### Core Backend

* [x] Project Setup
* [x] PostgreSQL Integration
* [x] Flyway Integration
* [x] Catalog Module
* [x] Global Exception Handling

### Inventory

* [ ] Inventory Management
* [ ] Stock Reservation
* [ ] Stock Release

### Orders

* [ ] Order Placement
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

### Deployment

* [ ] Dockerization
* [ ] CI/CD Pipeline

---

# Learning Objectives

This project focuses on gaining practical experience with:

* Spring Boot Architecture
* Domain-Driven Design Principles
* Modular Monolith Design
* Database Versioning
* Event-Driven Architecture
* Distributed Systems Patterns
* Scalability and Service Decomposition

---

# Status

🚧 Active Development

Current Implemented Module:

* Catalog

Next Module:

* Inventory
