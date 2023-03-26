CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE lease_contract
(
    id UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    rent_amount             NUMERIC(10, 2),
    rent_charges            NUMERIC(10, 2),
    date_contract_signature DATE,
    owner_id uuid,
    CONSTRAINT fk_owner_to_lease_contract
        FOREIGN KEY (owner_id) REFERENCES owner (id),
    tenant_id uuid,
    CONSTRAINT fk_tenant_to_lease_contract
        FOREIGN KEY (tenant_id) REFERENCES tenant (id),
    property_id uuid,
    CONSTRAINT fk_property_to_lease_contract
        FOREIGN KEY (property_id) REFERENCES property (id)
);

INSERT INTO lease_contract(id, rent_amount, rent_charges, date_contract_signature, owner_id, tenant_id, property_id)
VALUES ('8e04ccc1-bdb1-4d70-aec9-2ebf7b8da77f', '1250', '150', '2022/06/23', 'ac5630fb-5fab-4eea-b0f8-4f25b4ed8a30',
        'c3e97bb7-528d-478d-ab06-49f9308742d8', 'f1323f0f-1042-45d9-9cf8-5c77730c417e');

