-- Dodanie użytkowników
insert into
    users (email, password, first_name, last_name)
values
    ('admin@example.com', '$2b$12$IPfWYIgo0ouALrPr/t02xOx67YHFkfoQMmLbRcCKoRPsfPuobxOB.', 'Admin', 'Example'),-- <<pass: admin@example
    ('user@example.com', '$2b$12$f5a916UYzz6OfqCgVncvqOjlSRaLfBIt6dzBZ1EvDt/aFMdxf32UK', 'John', 'Doe'),-- <<pass: user@example
    ('anna.kowalska@example.com', '$2b$12$N6gwumWzngcRuyLGAajZquvJ.DuzRMcbIAOnjX2Mwxvej.LGEwaEK', 'Anna', 'Kowalska'),-- <<pass: anna.kowalska@example
    ('jan.nowak@example.com', '$2b$12$rxIzfhwuSseab6w.QJtXHugnKitGWjY31XHK8wccuPGb8XfCRWYEC', 'Jan', 'Nowak'),-- <<pass: jan.nowak@example
    ('ola@gmail.com', '$2b$12$xDp4L4c7euZI5iSObhFDw.9Qij9ftfRhnzWd07gWsevctbWoDX5rm', 'Ola', 'Kowalska');-- <<pass: ola@gmail
-- admin@example.com -- $2a$12$M5uX2zGbXpO7HREUkAdL/.FqO9vXjaySHBnVOULmk/dmspb7vAfcW << original password
-- user@example.com -- $2y$10$H9B0umVBgUUE4AZVvzvomONcA.aXjo6ZaL7Wuvku719Nl8PGESsN6 << original password
-- anna.kowalska@example.com -- $2y$10$H9B0umVBgUUE4AZVvzvomONcA.aXjo6ZaL7Wuvku719Nl8PGESsN6 << original password
-- jan.nowak@example.com -- $2y$10$H9B0umVBgUUE4AZVvzvomONcA.aXjo6ZaL7Wuvku719Nl8PGESsN6 << original password
-- ola@gmail.com -- $2y$10$H9B0umVBgUUE4AZVvzvomONcA.aXjo6ZaL7Wuvku719Nl8PGESsN6 << original password

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
