CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE client
(
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    last_name    VARCHAR(50) NOT NULL,
    first_name   VARCHAR(50) NOT NULL,
    birthday     DATE,
    phone_number VARCHAR(10) NOT NULL,
    email        VARCHAR(50),
    address_id uuid,
    CONSTRAINT fk_client_to_address
    FOREIGN KEY (address_id) REFERENCES address (id)
);

INSERT INTO client(id, last_name, first_name, birthday, phone_number, email, address_id)
VALUES ('c3e97bb7-528d-478d-ab06-49f9308742d8', 'Rey', 'Véronique', '1965-06-23', '0987654321', 'veronique@example.com',
        '985c0471-75c4-48b9-a8c9-2391ba4dd6d6'),
       ('d89cdbe4-9458-49cc-bec4-9be7e26e9ed2', 'Cazals', 'Jean-Jacques', '1989-02-21', '0123456789',
        'jean-jacques@example.com', '284a9934-a5c1-4995-8a36-9bdff9977fd7')
