CREATE TABLE accounts (
                         id BIGSERIAL PRIMARY KEY,
                         user_id BIGINT NOT NULL UNIQUE,
                         initial_balance DECIMAL(19, 2) NOT NULL,
                         actual_balance DECIMAL(19, 2) NOT NULL
);

ALTER TABLE accounts
    ADD CONSTRAINT fk_account_user
        FOREIGN KEY (user_id) REFERENCES "users" (id);