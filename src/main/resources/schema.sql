CREATE TABLE IF NOT EXISTS brands (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(255) UNIQUE NOT NULL,
    is_deleted BOOLEAN NOT NULL
    );

CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    brand_id BIGINT,
    amount BIGINT NOT NULL,
    created_date_time TIMESTAMP NOT NULL,
    updated_date_time TIMESTAMP NOT NULL,
    is_deleted BOOLEAN NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES brands(id)
    );

CREATE INDEX idx_category ON products (category);
CREATE INDEX idx_brand_id ON products (brand_id);
