CREATE TABLE kl_authority
(
    name VARCHAR(50) PRIMARY KEY NOT NULL

);

INSERT INTO kl_authority (name)
VALUES ('ADMIN'),
       ('USER');
