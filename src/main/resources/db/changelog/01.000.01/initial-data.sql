INSERT INTO users (name, date_of_birth, password, primary_email)
VALUES ('Andrei', '1992-11-04', '$2y$10$rj2.02q77p5ZKPOBQPa0ReRgT3fy.yQkMKqR1RtGMOV1Jt5NOiwzS', 'example1@example.com'),
       ('Valera', '2008-11-04', '$2y$10$rj2.02q77p5ZKPOBQPa0ReRgT3fy.yQkMKqR1RtGMOV1Jt5NOiwzS', 'example2@example.com'),
       ('Olga', '1985-06-12', '$2y$10$rj2.02q77p5ZKPOBQPa0ReRgT3fy.yQkMKqR1RtGMOV1Jt5NOiwzS', 'example3@example.com'),
       ('Ivan', '1990-01-01', '$2y$10$rj2.02q77p5ZKPOBQPa0ReRgT3fy.yQkMKqR1RtGMOV1Jt5NOiwzS', 'example4@example.com'),
       ('Daria', '1995-09-23', '$2y$10$rj2.02q77p5ZKPOBQPa0ReRgT3fy.yQkMKqR1RtGMOV1Jt5NOiwzS', 'example5@example.com');

INSERT INTO accounts (user_id, initial_balance, actual_balance)
VALUES (1, 100.00, 100.00),
       (2, 50.00, 50.00),
       (3, 200.00, 200.00),
       (4, 300.00, 300.00),
       (5, 150.00, 150.00);

INSERT INTO email_data (user_id, email)
VALUES (1, 'andrei@example.com'),
       (2, 'valera@example.com'),
       (3, 'olga@example.com'),
       (4, 'ivan@example.com'),
       (5, 'daria@example.com');

INSERT INTO phone_data (user_id, phone)
VALUES (1, '71234567890'),
       (2, '79876543210'),
       (3, '79001234567'),
       (4, '79998887766'),
       (5, '71112223344');
