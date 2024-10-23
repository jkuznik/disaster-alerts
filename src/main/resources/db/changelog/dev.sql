-- liquibase formatted sql


-- changeset Slawek84PL:1728934675080-1
CREATE TABLE alerts
(
    id            UUID                        NOT NULL,
    version       BIGINT                      NOT NULL,
    create_date   TIMESTAMP WITHOUT TIME ZONE,
    update_date   TIMESTAMP WITHOUT TIME ZONE,
    disaster_id   UUID,
    description   VARCHAR(255)                NOT NULL,
    creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_alerts PRIMARY KEY (id)
);

-- changeset Slawek84PL:1728934675080-2
CREATE TABLE disasters
(
    id                  UUID                        NOT NULL,
    version             BIGINT                      NOT NULL,
    create_date         TIMESTAMP WITHOUT TIME ZONE,
    update_date         TIMESTAMP WITHOUT TIME ZONE,
    type                SMALLINT                    NOT NULL,
    source              VARCHAR(255)                NOT NULL,
    location            VARCHAR(255)                NOT NULL,
    disaster_start_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    disaster_end_time   TIMESTAMP WITHOUT TIME ZONE,
    status              SMALLINT                    NOT NULL,
    CONSTRAINT pk_disasters PRIMARY KEY (id)
);

-- changeset Slawek84PL:1728934675080-3
CREATE TABLE disasters_alerts
(
    disaster_id UUID NOT NULL,
    alerts_id   UUID NOT NULL
);

-- changeset Slawek84PL:1728934675080-4
CREATE TABLE users
(
    id           UUID         NOT NULL,
    version      BIGINT       NOT NULL,
    create_date  TIMESTAMP WITHOUT TIME ZONE,
    update_date  TIMESTAMP WITHOUT TIME ZONE,
    username     VARCHAR(15),
    password     VARCHAR(255),
    email        VARCHAR(255) NOT NULL,
    phone_number VARCHAR(9),
    location     VARCHAR(255),
    role         VARCHAR(255),

    CONSTRAINT pk_users PRIMARY KEY (id)
);

-- changeset Slawek84PL:1728934675080-5
ALTER TABLE disasters_alerts
    ADD CONSTRAINT uc_disasters_alerts_alerts UNIQUE (alerts_id);

-- changeset Slawek84PL:1728934675080-6
ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

-- changeset Slawek84PL:1728934675080-7
ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

-- changeset Slawek84PL:1728934675080-8
ALTER TABLE alerts
    ADD CONSTRAINT FK_ALERTS_ON_DISASTER FOREIGN KEY (disaster_id) REFERENCES disasters (id);

-- changeset Slawek84PL:1728934675080-9
ALTER TABLE disasters_alerts
    ADD CONSTRAINT fk_disale_on_alert FOREIGN KEY (alerts_id) REFERENCES alerts (id);

-- changeset Slawek84PL:1728934675080-10
ALTER TABLE disasters_alerts
    ADD CONSTRAINT fk_disale_on_disaster FOREIGN KEY (disaster_id) REFERENCES disasters (id);

-- changeset Slawek84PL:1728934675080-11
insert into users
(id,version, username, create_date, email, password, role, location, phone_number)
values (gen_random_uuid(), 0, 'Disaster Admin', CURRENT_TIMESTAMP, 'admin@disaster.pl',
        '$2a$10$g0Q0DJVcBKJQePkw1b/US.dOeTaVKFTQ3i5w6iKPNx1K8tT03LzlS',
        'ROLE_ADMIN', 'Warszawa', '123456789'),
       (gen_random_uuid(), 0, 'Disaster User', CURRENT_TIMESTAMP, 'user@disaster.pl',
        '$2a$10$ZVw40XRuqbY115/bqwXjyuolLI0F4z76rTujUC1spcsH4JT/il.2S',
        'ROLE_USER', 'Kraków', '987654321');

-- changeset jkuznik:1728934675080-12
ALTER TABLE disasters
    ADD COLUMN description VARCHAR;

-- changeset jkuznik:1728934675080-13
ALTER TABLE disasters
    ADD COLUMN user_email VARCHAR(255) NOT NULL;

-- changeset jkuznik:1728934675080-14
ALTER TABLE alerts
    ADD COLUMN location VARCHAR;

-- changeset jkuznik:1728934675080-15
ALTER TABLE alerts
    ADD COLUMN username VARCHAR;

-- changeset jkuznik:1728934675080-16
ALTER TABLE disasters
ALTER COLUMN status TYPE VARCHAR(255),
    ALTER COLUMN type TYPE VARCHAR(255);

-- changeset jkuznik:1728934675080-17
ALTER TABLE disasters
    ADD COLUMN user_id UUID;

-- changeset jkuznik:1728934675080-18
ALTER TABLE disasters
    DROP COLUMN user_email;