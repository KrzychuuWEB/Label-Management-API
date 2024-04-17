--liquibase formatted sql

--changeset krzysztof:3
ALTER TABLE users
ADD first_name VARCHAR(80);