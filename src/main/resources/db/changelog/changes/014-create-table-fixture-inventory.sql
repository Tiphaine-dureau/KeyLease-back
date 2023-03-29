CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE fixture_inventory
(
    id UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    arrival_fixture_inventory_date DATE,
    exit_fixture_inventory_date    DATE,
    arrival_comments                VARCHAR(255),
    exit_comments                   VARCHAR(255),
    property_id UUID,
    CONSTRAINT fk_property_to_fixture_inventory
        FOREIGN KEY (property_id) REFERENCES property (id)
);

INSERT INTO fixture_inventory (id, arrival_fixture_inventory_date, exit_fixture_inventory_date, arrival_comments,
                               exit_comments, property_id)
VALUES ('165006ac-eb7e-4766-85a1-5a82943ef659', '2022-06-23', NULL,
        'Maison en bon état, murs propres, rayure sur le carrelage dans la cuisine, four encastré et hotte en état de marche', NULL,
        'f1323f0f-1042-45d9-9cf8-5c77730c417e');
