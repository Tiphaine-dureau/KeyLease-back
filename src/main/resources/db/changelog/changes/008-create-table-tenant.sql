CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE tenant
(
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    partner_last_name VARCHAR(50),
    partner_first_name VARCHAR(50),
    partner_phone_number VARCHAR(10),
    FOREIGN KEY (id) REFERENCES client(id)
);

INSERT INTO tenant (id, partner_last_name, partner_first_name, partner_phone_number)
VALUES ('c3e97bb7-528d-478d-ab06-49f9308742d8', 'Doe', 'John','0912345678'),
       ('d89cdbe4-9458-49cc-bec4-9be7e26e9ed2','','','')
