--liquibase formatted sql

--changeset krzysztof:7
CREATE TABLE nutritional_values (
   id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(50) NOT NULL,
   priority INT NOT NULL,
   created_at DATETIME NOT NULL,
   CONSTRAINT pk_nutritional_values PRIMARY KEY (id)
);