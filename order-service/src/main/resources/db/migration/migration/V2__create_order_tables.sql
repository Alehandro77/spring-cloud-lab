CREATE TABLE orders (
    id          BIGSERIAL PRIMARY KEY,
    buyer_id    BIGINT NOT NULL,
    status      VARCHAR(30) NOT NULL DEFAULT 'CREATED',
    total_price NUMERIC(12, 2) NOT NULL DEFAULT 0 CHECK (total_price >= 0),
    created_at  TIMESTAMP NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE order_items (
     id           BIGSERIAL PRIMARY KEY,
     order_id     BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
     product_id   BIGINT NOT NULL,
     product_name VARCHAR(255) NOT NULL,
     price        NUMERIC(12, 2) NOT NULL,
     size         VARCHAR(10),
     quantity     INT NOT NULL CHECK (quantity > 0)
);

CREATE INDEX idx_orders_buyer  ON orders(buyer_id);
CREATE INDEX idx_orders_status ON orders(status);