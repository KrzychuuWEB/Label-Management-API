--liquibase formatted sql

--changeset krzysztof:14
ALTER TABLE batches
ADD COLUMN product_id BIGINT;

--changeset krzysztof:15
ALTER TABLE batches
ADD CONSTRAINT fk_batches_products
FOREIGN KEY (product_id)
REFERENCES products(id);