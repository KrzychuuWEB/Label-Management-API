--liquibase formatted sql

--changeset krzysztof:16
DROP TABLE nutritional_values;
DROP TABLE sub_nutritional_values;

--changeset krzysztof:17
CREATE TABLE nutritional_values (
   id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(50) NOT NULL,
   priority DECIMAL(2,2) NOT NULL,
   created_at DATETIME NOT NULL,
   CONSTRAINT pk_nutritional_values PRIMARY KEY (id)
);