--liquibase formatted sql

--changeset krzysztof:9
CREATE TABLE initials (
   id BIGINT AUTO_INCREMENT NOT NULL,
   first_name VARCHAR(50) NOT NULL,
   last_name VARCHAR(100) NOT NULL,
   name VARCHAR(15) NOT NULL,
   user_id BIGINT NOT NULL,
   CONSTRAINT pk_initials PRIMARY KEY (id),
   FOREIGN KEY (user_id) REFERENCES users(id)
);