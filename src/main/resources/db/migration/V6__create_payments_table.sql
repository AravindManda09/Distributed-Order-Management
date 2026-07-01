CREATE TABLE payments
(
    id BIGSERIAL PRIMARY KEY,

    order_id BIGINT NOT NULL,

    amount DECIMAL(12,2) NOT NULL,

    status VARCHAR(30) NOT NULL,

    created_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_payment_order
    ON payments(order_id);