CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE kl_user
(
    id uuid PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    login VARCHAR(255) NOT NULL

)
