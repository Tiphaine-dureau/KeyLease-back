CREATE TABLE rental_management_fees
(
    id       SERIAL PRIMARY KEY NOT NULL,
    fee_rate DOUBLE PRECISION   NOT NULL
);

INSERT INTO rental_management_fees(fee_rate)
VALUES (0.08)
