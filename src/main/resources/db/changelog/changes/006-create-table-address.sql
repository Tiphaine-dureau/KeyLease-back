CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE address
(
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    street             VARCHAR(255) NOT NULL,
    additional_address VARCHAR(255),
    zip_code           VARCHAR(5)   NOT NULL,
    town               VARCHAR(255) NOT NULL
);

INSERT INTO address (id, street, additional_address, zip_code, town)
VALUES ('985c0471-75c4-48b9-a8c9-2391ba4dd6d6', '2 rue Victor Hugo', 'Bat B Apt 31', '33130', 'Bègles'),
       ('284a9934-a5c1-4995-8a36-9bdff9977fd7', '1 rue Emile Zola', '', '64100', 'Bayonne');
