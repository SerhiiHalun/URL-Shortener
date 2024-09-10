CREATE SEQUENCE IF NOT EXISTS seq_user_id
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS seq_link_id
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS seq_roles_id
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS users
(
    id         BIGINT DEFAULT nextval('seq_user_id'),
    username VARCHAR(255),
    password  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS roles
(
    id BIGINT DEFAULT nextval('seq_roles_id'),
    name VARCHAR(250),
    CONSTRAINT pk_roles_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users_roles
(
    users_id BIGINT NOT NULL,
    roles_id BIGINT NOT NULL,
    PRIMARY KEY (users_id,roles_id),
    FOREIGN KEY (users_id) REFERENCES users(id),
    FOREIGN KEY (roles_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS link
(
    id          BIGINT DEFAULT nextval('seq_link_id'),
    full_url      VARCHAR(1000) UNIQUE,
    short_url     VARCHAR(8) UNIQUE,
    creation_date  DATE DEFAULT now(),
    order_status VARCHAR(8) NOT NULL,
    user_id          BIGINT,
    transition_counter BIGINT,
    CONSTRAINT pk_link_id PRIMARY KEY (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);
