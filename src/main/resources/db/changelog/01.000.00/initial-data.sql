INSERT INTO users (id, name, date_of_birth, password)
VALUES
    ('Andrei', '1992-11-04', 'password1'),
    ('Valera', '2008-11-04', 'password2'),
    ('Olga',   '1985-06-12', 'password3'),
    ('Ivan',   '1990-01-01', 'password4'),
    ('Daria',  '1995-09-23', 'password5');

INSERT INTO accounts (user_id, initial_balance, actual_balance)
VALUES
    (1, 100.00, 100.00),
    (2, 50.00, 50.00),
    (3, 200.00, 200.00),
    (4, 300.00, 300.00),
    (5, 150.00, 150.00);

INSERT INTO email_data (user_id, email)
VALUES
    (1, 'andrei@example.com'),
    (2, 'valera@example.com'),
    (3, 'olga@example.com'),
    (4, 'ivan@example.com'),
    (5, 'daria@example.com');

INSERT INTO phone_data (user_id, phone)
VALUES
    (1, '71234567890'),
    (2, '79876543210'),
    (3, '79001234567'),
    (4, '79998887766'),
    (5, '71112223344');
