CREATE USER auth_user WITH PASSWORD 'auth_pass';
CREATE DATABASE auth_db OWNER auth_user;
\c auth_db
GRANT ALL ON SCHEMA public TO auth_user;
GRANT ALL ON SCHEMA public TO public;

CREATE USER product_user WITH PASSWORD 'product_pass';
CREATE DATABASE product_db OWNER product_user;
\c product_db
GRANT ALL ON SCHEMA public TO product_user;
GRANT ALL ON SCHEMA public TO public;

CREATE USER order_user WITH PASSWORD 'order_pass';
CREATE DATABASE order_db OWNER order_user;
\c order_db
GRANT ALL ON SCHEMA public TO order_user;
GRANT ALL ON SCHEMA public TO public;

\q