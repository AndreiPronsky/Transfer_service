CREATE TABLE email_data (
                            id BIGSERIAL PRIMARY KEY,
                            user_id BIGINT,
                            email VARCHAR(200) NOT NULL UNIQUE
);

ALTER TABLE email_data
    ADD CONSTRAINT fk_email_data_user
        FOREIGN KEY (user_id) REFERENCES "users"(id);