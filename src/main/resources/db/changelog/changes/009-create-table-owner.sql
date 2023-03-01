CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE owner
(
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    iban VARCHAR(33),
    FOREIGN KEY (id) REFERENCES client(id)
);

INSERT INTO address(id, street, additional_address, zip_code, town)
VALUES ('7a364b33-20ed-4b0d-8e86-51018d98b6d8', '1 rue des Tulipes', 'B1 apt 11', '64500', 'Saint-Jean-de-Luz');

INSERT INTO client(id, last_name, first_name, birthday, phone_number, email, address_id, client_type)
VALUES('ac5630fb-5fab-4eea-b0f8-4f25b4ed8a30', 'Madur', 'Virginie', '1961-09-13', '0687687321', 'virginie@example.com',
       '7a364b33-20ed-4b0d-8e86-51018d98b6d8','O');

INSERT INTO owner(id, iban)
VALUES ('ac5630fb-5fab-4eea-b0f8-4f25b4ed8a30','FR76 0000 9999 8888 7777 6666 555');
