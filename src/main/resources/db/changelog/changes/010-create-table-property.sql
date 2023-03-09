CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE property
(
  id UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  area VARCHAR(5),
  rooms_number VARCHAR(3),
  description VARCHAR(600),
  address_id uuid,
  CONSTRAINT fk_property_to_address
    FOREIGN KEY (address_id) REFERENCES address (id)
);

INSERT INTO address(id, street, additional_address, zip_code, town)
VALUES  ('b759dbd5-f197-4068-ae4e-4f0d7ebf1661', '1 rue des Mimosas', '', '33000', 'Bordeaux'),
        ('4e956687-76d2-47ff-8e2c-75eeb5518bb3', '1 rue des Roses', 'Bat C Apt 10', '64500', 'Ciboure');

INSERT INTO property (id, area, rooms_number, description, address_id)
VALUES('f1323f0f-1042-45d9-9cf8-5c77730c417e', '110','5', 'Maison de 5 pièces mesurant 110m2 située non loin du centre ville. Elle se compose de 3 chambres, d''une cuisine fermée, d''un double séjour, d''une salle de bain avec wc séparé, d''un garage et d''un jardin de 100m2','b759dbd5-f197-4068-ae4e-4f0d7ebf1661'),
      ('ae379cbe-6ca1-49b0-bc7c-3cb584a20393', '54','2','Appartement de 2 pièces composé d''un séjour avec cuisine ouverte, d''une chambre, d''un bureau, d''une salle de bain avec WC séparé. Un joli balcon plein sud et une place de parking agrémentent le tout', '4e956687-76d2-47ff-8e2c-75eeb5518bb3');
