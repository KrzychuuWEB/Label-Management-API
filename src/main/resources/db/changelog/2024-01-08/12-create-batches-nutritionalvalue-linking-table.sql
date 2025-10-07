--liquibase formatted sql

--changeset krzysztof:12
CREATE TABLE batches_nutritional_values (
    batch_id BIGINT NOT NULL,
    nutritional_value_id BIGINT,
    sub_nutritional_value_id BIGINT,
    value VARCHAR(40) NOT NULL,
    CONSTRAINT pk_batches_nutritional_values PRIMARY KEY (batch_id, nutritional_value_id, sub_nutritional_value_id),
    FOREIGN KEY (batch_id) REFERENCES batches(id),
    FOREIGN KEY (nutritional_value_id) REFERENCES nutritional_values(id),
    FOREIGN KEY (sub_nutritional_value_id) REFERENCES sub_nutritional_values(id)
);