CREATE TABLE kl_user_authority
(
    user_id        UUID        NOT NULL,
    authority_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, authority_name),
    FOREIGN KEY (user_id) REFERENCES kl_user (id),
    FOREIGN KEY (authority_name) REFERENCES kl_authority (name)
);


INSERT INTO kl_user_authority (user_id, authority_name)
VALUES ('9ca72d27-bfd4-4dfa-a56f-c98dc008fab0', 'ADMIN'),
       ('9ca72d27-bfd4-4dfa-a56f-c98dc008fab0', 'USER'),
       ('d49d46ea-6c6d-46c2-bfdb-25b91ea8052b', 'USER');
