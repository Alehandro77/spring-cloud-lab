CREATE TABLE carts (
    id          BIGSERIAL PRIMARY KEY,
    buyer_id    BIGINT NOT NULL UNIQUE,
    created_at  TIMESTAMP NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE cart_items (
    id          BIGSERIAL PRIMARY KEY,
    cart_id     BIGINT NOT NULL REFERENCES carts(id) ON DELETE CASCADE,
    product_id  BIGINT NOT NULL,
    title       VARCHAR(255) NOT NULL,
    price       NUMERIC(12, 2) NOT NULL,
    quantity    INT NOT NULL CHECK (quantity > 0),
    added_at    TIMESTAMP NOT NULL DEFAULT now(),
    UNIQUE (cart_id, product_id)
);