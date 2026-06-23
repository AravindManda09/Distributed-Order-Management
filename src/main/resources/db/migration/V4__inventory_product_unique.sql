ALTER TABLE inventory_items
    ADD CONSTRAINT uk_inventory_product
        UNIQUE (product_id);