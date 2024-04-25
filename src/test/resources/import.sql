-- Roles
INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');

-- Users
INSERT INTO users (id, account_enabled, account_locked, created_at, email, email_verified, password, updated_at, username) VALUES (1, 't', 'f', '2024-04-22 21:03:38.230524', 'rgdevment@github.com', 'f', '$2a$10$.I2NP7kQcLrH8w51htpNP.4kjU4tqGB4VlvGtPYXIctMoEwk6AtQW', '2024-04-22 21:03:38.230539', 'enable');
INSERT INTO users (id, account_enabled, account_locked, created_at, email, email_verified, password, updated_at, username) VALUES (2, 'f', 'f', '2024-04-25 11:03:38.230524', 'rgdevment@gitlab.com', 'f', '$2a$10$.I2NP7kQcLrH8w51htpNP.4kjU4tqGB4VlvGtPYXIctMoEwk6AtQW', '2024-04-25 11:03:38.230539', 'disable');
INSERT INTO users (id, account_enabled, account_locked, created_at, email, email_verified, password, updated_at, username) VALUES (3, 't', 'f', '2024-04-25 11:05:38.230524', 'rgdevment@linkedin.com', 'f', '$2a$10$.I2NP7kQcLrH8w51htpNP.4kjU4tqGB4VlvGtPYXIctMoEwk6AtQW', '2024-04-25 11:05:38.230539', 'admin');

-- User Roles
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (3, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (3, 2);
