--liquibase formatted sql

--changeset krzysztof:1
CREATE TABLE users (
   id BIGINT AUTO_INCREMENT NOT NULL,
   email VARCHAR(255) NOT NULL,
   username VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   created_at DATETIME NOT NULL,
   CONSTRAINT pk_users PRIMARY KEY (id)
);