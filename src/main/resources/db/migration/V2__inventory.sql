CREATE TABLE inventory_items (
                                 id BIGSERIAL PRIMARY KEY,
                                 product_id BIGINT NOT NULL,
                                 quantity_available INTEGER NOT NULL,
                                 quantity_reserved INTEGER NOT NULL DEFAULT 0
);