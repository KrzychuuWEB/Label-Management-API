--liquibase formatted sql

--changeset krzysztof:2
CREATE TABLE companies (
   id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NOT NULL,
   footer text NOT NULL,
   created_at DATETIME NOT NULL,
   user_id BIGINT NOT NULL,
   CONSTRAINT pk_companies PRIMARY KEY (id),
   FOREIGN KEY (user_id) REFERENCES users(id)
);