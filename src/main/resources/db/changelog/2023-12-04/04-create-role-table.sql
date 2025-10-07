--liquibase formatted sql

--changeset krzysztof:4
CREATE TABLE roles (
   id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(50) NOT NULL,
   CONSTRAINT pk_users PRIMARY KEY (id)
);