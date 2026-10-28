INSERT INTO products (name, description, price, brand, category_id, gender, color, stock_quantity, image_url) VALUES
      ('Футболка оверсайз', 'Хлопковая футболка свободного кроя', 1490.00, 'Nike', (SELECT id FROM categories WHERE name='Футболки'), 'UNISEX', 'белый', 50, 'https://via.placeholder.com/300'),
      ('Футболка базовая', 'Классическая футболка', 990.00, 'H&M', (SELECT id FROM categories WHERE name='Футболки'), 'MEN', 'чёрный', 80, 'https://via.placeholder.com/300'),
      ('Джинсы скинни', 'Узкие джинсы', 3490.00, 'Levis', (SELECT id FROM categories WHERE name='Джинсы'), 'WOMEN', 'синий', 30, 'https://via.placeholder.com/300'),
      ('Джинсы прямые', 'Классические прямые джинсы', 3990.00, 'Levis', (SELECT id FROM categories WHERE name='Джинсы'), 'MEN', 'синий', 25, 'https://via.placeholder.com/300'),
      ('Куртка зимняя', 'Тёплая зимняя куртка', 8990.00, 'Columbia', (SELECT id FROM categories WHERE name='Куртки'), 'UNISEX', 'чёрный', 15, 'https://via.placeholder.com/300'),
      ('Куртка демисезон', 'Лёгкая куртка на весну/осень', 5990.00, 'The North Face', (SELECT id FROM categories WHERE name='Куртки'), 'MEN', 'серый', 20, 'https://via.placeholder.com/300'),
      ('Платье вечернее', 'Элегантное платье', 7990.00, 'Zara', (SELECT id FROM categories WHERE name='Платья'), 'WOMEN', 'красный', 10, 'https://via.placeholder.com/300'),
      ('Платье летнее', 'Лёгкое летнее платье', 2990.00, 'H&M', (SELECT id FROM categories WHERE name='Платья'), 'WOMEN', 'белый', 25, 'https://via.placeholder.com/300'),
      ('Кроссовки беговые', 'Беговые кроссовки', 5990.00, 'Adidas', (SELECT id FROM categories WHERE name='Обувь'), 'UNISEX', 'белый', 40, 'https://via.placeholder.com/300'),
      ('Кроссовки повседневные', 'Удобные кроссовки', 4990.00, 'Nike', (SELECT id FROM categories WHERE name='Обувь'), 'MEN', 'чёрный', 35, 'https://via.placeholder.com/300'),
      ('Шарф вязаный', 'Тёплый шарф', 1290.00, 'H&M', (SELECT id FROM categories WHERE name='Аксессуары'), 'UNISEX', 'серый', 60, 'https://via.placeholder.com/300'),
      ('Шапка зимняя', 'Шерстяная шапка', 990.00, 'Columbia', (SELECT id FROM categories WHERE name='Аксессуары'), 'UNISEX', 'чёрный', 70, 'https://via.placeholder.com/300'),
      ('Рубашка классическая', 'Офисная рубашка', 2990.00, 'Zara', (SELECT id FROM categories WHERE name='Рубашки'), 'MEN', 'белый', 40, 'https://via.placeholder.com/300'),
      ('Рубашка в клетку', 'Рубашка фланель', 2490.00, 'H&M', (SELECT id FROM categories WHERE name='Рубашки'), 'MEN', 'красный', 30, 'https://via.placeholder.com/300'),
      ('Свитер крупной вязки', 'Тёплый свитер', 3990.00, 'Zara', (SELECT id FROM categories WHERE name='Свитера'), 'UNISEX', 'бежевый', 25, 'https://via.placeholder.com/300'),
      ('Свитер с горлом', 'Свитер с высоким воротом', 3490.00, 'H&M', (SELECT id FROM categories WHERE name='Свитера'), 'WOMEN', 'серый', 20, 'https://via.placeholder.com/300'),
      ('Футболка с принтом', 'Футболка с графическим принтом', 1790.00, 'Nike', (SELECT id FROM categories WHERE name='Футболки'), 'UNISEX', 'синий', 45, 'https://via.placeholder.com/300'),
      ('Джинсы рваные', 'Модные рваные джинсы', 4290.00, 'Levis', (SELECT id FROM categories WHERE name='Джинсы'), 'WOMEN', 'голубой', 15, 'https://via.placeholder.com/300'),
      ('Ботинки кожаные', 'Классические кожаные ботинки', 7990.00, 'Timberland', (SELECT id FROM categories WHERE name='Обувь'), 'MEN', 'коричневый', 12, 'https://via.placeholder.com/300'),
      ('Сумка женская', 'Кожаная сумка', 5990.00, 'Zara', (SELECT id FROM categories WHERE name='Аксессуары'), 'WOMEN', 'чёрный', 18, 'https://via.placeholder.com/300');

-- Размеры
INSERT INTO product_sizes (product_id, size)
SELECT id, s.size FROM products, (VALUES ('S'),('M'),('L'),('XL')) AS s(size)
WHERE category_id IN (SELECT id FROM categories WHERE name IN ('Футболки','Свитера','Рубашки'));

INSERT INTO product_sizes (product_id, size)
SELECT id, s.size FROM products, (VALUES ('XS'),('S'),('M'),('L')) AS s(size)
WHERE category_id IN (SELECT id FROM categories WHERE name = 'Платья');