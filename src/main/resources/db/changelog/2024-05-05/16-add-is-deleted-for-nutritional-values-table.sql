--liquibase formatted sql

--changeset krzysztof:18
ALTER TABLE nutritional_values
ADD COLUMN is_deleted BOOLEAN;