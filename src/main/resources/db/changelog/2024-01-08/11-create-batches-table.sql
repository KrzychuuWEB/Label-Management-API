--liquibase formatted sql

--changeset krzysztof:11
CREATE TABLE batches (
   id BIGINT AUTO_INCREMENT NOT NULL,
   serial VARCHAR(80) NOT NULL,
   expiration_date DATE NOT NULL,
   short_date BOOLEAN NOT NULL,
   country VARCHAR(40) NOT NULL,
   created_at DATETIME NOT NULL,
   CONSTRAINT pk_batches PRIMARY KEY (id),
);