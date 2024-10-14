-- liquibase formatted sql

-- changeset Slawek84PL:1728906733556-1
CREATE TABLE users
(
    id           UUID         NOT NULL,
    username     VARCHAR(15),
    password     VARCHAR(255),
    email        VARCHAR(255) NOT NULL,
    phone_number VARCHAR(9),
    location     VARCHAR(255),
    role         VARCHAR(255),
    create_date  date,
    update_date  date,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

-- changeset Slawek84PL:1728906733556-2
ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

-- changeset Slawek84PL:1728906733556-3
ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

