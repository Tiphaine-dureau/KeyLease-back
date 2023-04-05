CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE payment
(
    id UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    paid_rent         NUMERIC(10, 2) NOT NULL,
    rent_payment_date DATE           NOT NULL,
    lease_contract_id UUID,
    CONSTRAINT fk_payment_to_lease_contract
        FOREIGN KEY (lease_contract_id) REFERENCES lease_contract (id)
);

INSERT INTO payment(id, paid_rent, rent_payment_date, lease_contract_id)
VALUES ('5d9f119b-c4e7-4708-9004-4fe258edebd3', '1250', '2022/07/05', '8e04ccc1-bdb1-4d70-aec9-2ebf7b8da77f'),
       ('68e5e67f-ec87-4d77-9128-1089ff863bdc', '1250', '2022/08/05', '8e04ccc1-bdb1-4d70-aec9-2ebf7b8da77f'),
       ('fd557b97-5c67-4244-bf8b-47dbdca6dee8', '1250', '2022/09/05', '8e04ccc1-bdb1-4d70-aec9-2ebf7b8da77f'),
       ('ebfd1c45-f752-4d01-aaf8-7be1750ad636', '1250', '2022/10/05', '8e04ccc1-bdb1-4d70-aec9-2ebf7b8da77f'),
       ('e426d55e-f79b-4e2c-a642-e27cca187090', '1250', '2022/11/05', '8e04ccc1-bdb1-4d70-aec9-2ebf7b8da77f'),
       ('554849f6-3d57-438e-8eb4-ca24132def05', '1250', '2022/12/05', '8e04ccc1-bdb1-4d70-aec9-2ebf7b8da77f'),
       ('93e8a9b7-f291-4c65-9186-d13199008b7a', '1000', '2023/01/05', '8e04ccc1-bdb1-4d70-aec9-2ebf7b8da77f'),
       ('649659ee-9cd9-4df2-ad24-d1989377ed1b', '1250', '2023/02/05', '8e04ccc1-bdb1-4d70-aec9-2ebf7b8da77f'),
       ('28b55f04-e727-4700-ad91-5df9928efceb', '1000', '2023/03/05', '8e04ccc1-bdb1-4d70-aec9-2ebf7b8da77f')
