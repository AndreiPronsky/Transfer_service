CREATE TABLE phone_data (
                            id BIGSERIAL PRIMARY KEY,
                            user_id BIGINT,
                            phone VARCHAR(13) NOT NULL UNIQUE
);

ALTER TABLE phone_data
    ADD CONSTRAINT fk_phone_data_user
        FOREIGN KEY (user_id) REFERENCES "users"(id);