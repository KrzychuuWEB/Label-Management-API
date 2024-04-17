--liquibase formatted sql

--changeset krzysztof:10
CREATE TABLE initials (
   id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(80) NOT NULL,
   height DECIMAL(6,1) NOT NULL,
   width DECIMAL(6,1) NOT NULL,
   user_id BIGINT NOT NULL,
   created_at DATETIME NOT NULL,
   CONSTRAINT pk_template PRIMARY KEY (id),
   FOREIGN KEY (user_id) REFERENCES users(id)
);