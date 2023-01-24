ALTER TABLE kl_user
    RENAME COLUMN login TO email;

ALTER TABLE kl_user
    ADD COLUMN last_name  VARCHAR(255),
    ADD COLUMN first_name VARCHAR(255),
    ADD COLUMN password   VARCHAR(255);

UPDATE kl_user
SET last_name= 'du Reau',
    first_name='Tiphaine',
    password='$2y$10$0.E2u1Ef6Zt4VweuQVYMh.NTTUfRyUSQAC0UBHXlK6CIp6rQlj7hK'
WHERE email = 'admin@keylease.com';
UPDATE kl_user
SET last_name= 'du Reau',
    first_name='Amélie',
    password='$2y$10$UYW5O8MrYh5qFzKgXI8E7e8qaQGYPbvXvbKa2Qt6IKHAYRQ8HJI6G'
WHERE email = 'agent@keylease.com';

ALTER TABLE kl_user
    ALTER COLUMN password SET NOT NULL,
    ALTER COLUMN last_name SET NOT NULL,
    ALTER COLUMN first_name SET NOT NULL;


