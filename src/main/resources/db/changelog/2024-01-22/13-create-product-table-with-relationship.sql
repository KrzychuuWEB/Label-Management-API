--liquibase formatted sql

--changeset krzysztof:13
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT NOT NULL,
    user_id BIGINT NOT NULL,
    name VARCHAR(70) NOT NULL,
    description TEXT,
    composition TEXT,
    slug VARCHAR(80) NOT NULL,
    created_at DATETIME,
    CONSTRAINT pk_products PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
);