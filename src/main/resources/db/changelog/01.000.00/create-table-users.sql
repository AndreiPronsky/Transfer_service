CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(500) NOT NULL,
                       date_of_birth DATE,
                       password VARCHAR(500) NOT NULL
);