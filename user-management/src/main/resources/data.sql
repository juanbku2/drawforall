INSERT INTO role (description, name) VALUES ( 'Admin', 'ROLE_ADMIN');
INSERT INTO role (description, name) VALUES ( 'User create', 'ROLE_USER_CREATE');
INSERT INTO role (description, name) VALUES ( 'User update', 'ROLE_USER_UPDATE');
INSERT INTO role (description, name) VALUES ( 'User', 'ROLE_USER');

-- password = admin -> bncrypt
INSERT INTO users (username, password, email, first_name, last_name)
VALUES ('Goku', '$2y$12$GGNWF9TyjLsRhwhv3WxFHeu10251mFEBdQ.sW5AdJrpku5SlmA1fi', 'goku@dragonball.com', 'goku', 'Akira');
INSERT INTO users (username, password, email, first_name, last_name)
VALUES ('Gohan', '$2y$12$GGNWF9TyjLsRhwhv3WxFHeu10251mFEBdQ.sW5AdJrpku5SlmA1fi', 'gohan@dragonball.com', 'gohan', 'Akira');
INSERT INTO users (username, password, email, first_name, last_name)
VALUES ('goten', '$2y$12$GGNWF9TyjLsRhwhv3WxFHeu10251mFEBdQ.sW5AdJrpku5SlmA1fi', 'goten@dragonball.com', 'goten', 'Akira');
INSERT INTO users (username, password, email, first_name, last_name)
VALUES ('trunks', '$2y$12$GGNWF9TyjLsRhwhv3WxFHeu10251mFEBdQ.sW5AdJrpku5SlmA1fi', 'trunks@dragonball.com', 'Trunks', 'Vigita');
INSERT INTO users (username, password, email, first_name, last_name)
VALUES ('Killua', '$2y$12$GGNWF9TyjLsRhwhv3WxFHeu10251mFEBdQ.sW5AdJrpku5SlmA1fi', 'killua@hunterxhunter.com', 'Killua', 'Solidek');
INSERT INTO users (username, password, email, first_name, last_name)
VALUES ('Gon', '$2y$12$GGNWF9TyjLsRhwhv3WxFHeu10251mFEBdQ.sW5AdJrpku5SlmA1fi', 'gon@hunterxhunter.com', 'Gon', 'Frics');
INSERT INTO users (username, password, email, first_name, last_name)
VALUES ('Alluka', '$2y$12$GGNWF9TyjLsRhwhv3WxFHeu10251mFEBdQ.sW5AdJrpku5SlmA1fi', 'alluka@hunterxhunter.com', 'Alluka', 'Solidek');

INSERT INTO user_roles (user_id, role_id) VALUES ('1', '1');
INSERT INTO user_roles (user_id, role_id) VALUES ('1', '2');
INSERT INTO user_roles (user_id, role_id) VALUES ('2', '4');
INSERT INTO user_roles (user_id, role_id) VALUES ('3', '4');
INSERT INTO user_roles (user_id, role_id) VALUES ('4', '4');
INSERT INTO user_roles (user_id, role_id) VALUES ('5', '4');
INSERT INTO user_roles (user_id, role_id) VALUES ('6', '4');
INSERT INTO user_roles (user_id, role_id) VALUES ('7', '4');
