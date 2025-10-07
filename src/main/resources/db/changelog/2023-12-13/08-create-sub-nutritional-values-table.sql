--liquibase formatted sql

--changeset krzysztof:8
CREATE TABLE sub_nutritional_values (
   id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(50) NOT NULL,
   priority INT NOT NULL,
   nutritional_value_id BIGINT NOT NULL,
   created_at DATETIME NOT NULL,
   CONSTRAINT pk_sub_nutritional_values PRIMARY KEY (id),
   FOREIGN KEY (nutritional_value_id) REFERENCES nutritional_values(id)
);