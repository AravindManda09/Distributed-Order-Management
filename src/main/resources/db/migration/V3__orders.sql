CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        status VARCHAR(50) NOT NULL,
                        total_amount DECIMAL(10,2) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE order_items (
                             id BIGSERIAL PRIMARY KEY,

                             order_id BIGINT NOT NULL,

                             product_id BIGINT NOT NULL,

                             quantity INTEGER NOT NULL,

                             price DECIMAL(10,2) NOT NULL
);