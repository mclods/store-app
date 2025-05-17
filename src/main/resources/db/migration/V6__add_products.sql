CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(10, 2) NOT NULL,
    category_id SMALLINT NOT NULL REFERENCES categories(id) ON DELETE RESTRICT
);