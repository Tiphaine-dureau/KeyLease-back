CREATE TABLE property_type
(
    property_id UUID NOT NULL,
    type_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (property_id, type_name),
    FOREIGN KEY (property_id) REFERENCES property(id),
    FOREIGN KEY (type_name) REFERENCES type(name)
);

INSERT INTO property_type(property_id, type_name)
VALUES ('f1323f0f-1042-45d9-9cf8-5c77730c417e', 'Maison'),
       ('ae379cbe-6ca1-49b0-bc7c-3cb584a20393', 'Appartement');
