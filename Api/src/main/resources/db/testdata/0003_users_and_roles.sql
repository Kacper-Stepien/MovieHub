-- Dodanie użytkowników
insert into
    users (email, password, first_name, last_name)
values
    ('admin@example.com', '$2a$12$M5uX2zGbXpO7HREUkAdL/.FqO9vXjaySHBnVOULmk/dmspb7vAfcW', 'Admin', 'Example'),
    ('user@example.com', '$2y$10$H9B0umVBgUUE4AZVvzvomONcA.aXjo6ZaL7Wuvku719Nl8PGESsN6', 'John', 'Doe'),
    ('anna.kowalska@example.com', '$2y$10$H9B0umVBgUUE4AZVvzvomONcA.aXjo6ZaL7Wuvku719Nl8PGESsN6', 'Anna', 'Kowalska'),
    ('jan.nowak@example.com', '$2y$10$H9B0umVBgUUE4AZVvzvomONcA.aXjo6ZaL7Wuvku719Nl8PGESsN6', 'Jan', 'Nowak'),
    ('ola@gmail.com', '$2y$10$H9B0umVBgUUE4AZVvzvomONcA.aXjo6ZaL7Wuvku719Nl8PGESsN6', 'Ola', 'Kowalska');


-- Dodanie ról użytkowników
insert into
    user_role (name, description)
values
    ('ADMIN', 'pełne uprawnienia'),
    ('USER', 'podstawowe uprawnienia, możliwość oddawania głosów');

-- Przypisanie ról do użytkowników
insert into
    user_roles (user_id, role_id)
values
    (1, 1), -- Admin
    (2, 2), -- User
    (3, 2), -- User
    (4, 2), -- User
    (5,2);
