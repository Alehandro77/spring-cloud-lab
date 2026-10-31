CREATE TABLE categories (
                            id   BIGSERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE
);

INSERT INTO categories(name) VALUES
                                 ('Футболки'), ('Джинсы'), ('Куртки'), ('Платья'),
                                 ('Обувь'), ('Аксессуары'), ('Рубашки'), ('Свитера');

CREATE TABLE products (
                          id             BIGSERIAL PRIMARY KEY,
                          name           VARCHAR(255) NOT NULL,
                          description    TEXT,
                          price          NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
                          brand          VARCHAR(100),
                          category_id    BIGINT REFERENCES categories(id) ON DELETE SET NULL,
                          gender         VARCHAR(20) NOT NULL,         -- MEN, WOMEN, UNISEX
                          color          VARCHAR(50),
                          stock_quantity INT NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
                          image_url      VARCHAR(500),
                          is_active      BOOLEAN NOT NULL DEFAULT TRUE,
                          created_at     TIMESTAMP NOT NULL DEFAULT now(),
                          updated_at     TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE product_sizes (
                               product_id BIGINT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
                               size       VARCHAR(10) NOT NULL,             -- XS, S, M, L, XL, XXL
                               PRIMARY KEY (product_id, size)
);

CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_gender   ON products(gender);
CREATE INDEX idx_products_price    ON products(price);
CREATE INDEX idx_products_active   ON products(is_active);