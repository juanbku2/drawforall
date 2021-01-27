
CREATE TABLE users
(
    id           INT                 NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(128) UNIQUE NOT NULL,
    email        VARCHAR(128) UNIQUE NOT NULL,
    password     VARCHAR(255)        NOT NULL,
    first_name   VARCHAR(32)         NOT NULL,
    last_name    VARCHAR(32)         NOT NULL,
    pic_byte     varbinary(max)

);


CREATE TABLE role
(
    id          INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(32),
    name        VARCHAR(32)
);

CREATE TABLE user_roles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

ALTER TABLE user_roles
    ADD CONSTRAINT FK_role_id
        FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE;

ALTER TABLE user_roles
    ADD CONSTRAINT FK_user_id
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;