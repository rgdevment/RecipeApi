-- Roles
INSERT INTO roles (id, name) VALUES(1, 'ROLE_USER');
INSERT INTO roles (id, name) VALUES(2, 'ROLE_ADMIN');

-- Users
INSERT INTO users (id, account_enabled, created_at, email, email_verified, PASSWORD, updated_at, username) VALUES(1, 't', '2024-04-22 21:03:38.230524', 'rgdevment@github.com', 't', '$2a$10$.I2NP7kQcLrH8w51htpNP.4kjU4tqGB4VlvGtPYXIctMoEwk6AtQW', '2024-04-22 21:03:38.230539', 'user_enabled');
INSERT INTO users (id, account_enabled, created_at, email, email_verified, PASSWORD, updated_at, username) VALUES(2, 'f', '2024-04-25 11:03:38.230524', 'rgdevment@gitlab.com', 'f', '$2a$10$.I2NP7kQcLrH8w51htpNP.4kjU4tqGB4VlvGtPYXIctMoEwk6AtQW', '2024-04-25 11:03:38.230539', 'user_disabled');
INSERT INTO users (id, account_enabled, created_at, email, email_verified, PASSWORD, updated_at, username) VALUES(3, 't', '2024-04-25 11:05:38.230524', 'rgdevment@linkedin.com', 't', '$2a$10$.I2NP7kQcLrH8w51htpNP.4kjU4tqGB4VlvGtPYXIctMoEwk6AtQW', '2024-04-25 11:05:38.230539', 'user_admin');

-- User Roles
INSERT INTO users_roles (user_id, role_id) VALUES(1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES(2, 1);
INSERT INTO users_roles (user_id, role_id) VALUES(3, 1);
INSERT INTO users_roles (user_id, role_id) VALUES(3, 2);

-- User Tokens
INSERT INTO users_verification_token (id, code, created_at, user_id) VALUES(1, 'fake_code_1', '2024-04-22 21:03:38.230524', 1);
INSERT INTO users_verification_token (id, code, created_at, user_id) VALUES(2, 'fake_code_2', '2024-04-25 11:03:38.230524', 2);
INSERT INTO users_verification_token (id, code, created_at, user_id) VALUES(3, 'fake_code_3', '2024-04-25 11:05:38.230524', 3);

-- Ingredients
INSERT INTO ingredients (id, name, created_at, updated_at) VALUES(nextval('ingredients_seq'), 'Arroz', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), (nextval('ingredients_seq'), 'Pollo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), (nextval('ingredients_seq'), 'Tomate', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), (nextval('ingredients_seq'), 'Tortilla', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Units Measures
INSERT INTO units_measures (id, name) VALUES(nextval('units_measures_seq'), 'Grams'), (nextval('units_measures_seq'), 'Cups'), (nextval('units_measures_seq'), 'Pieces');

-- Recipes
INSERT INTO recipes (id, title, preparation, cooking_time, serving_size, origin_version, difficulty, user_id, created_at, updated_at) VALUES(nextval('recipes_seq'), 'Paella de mariscos', 'Preparación de la paella...', 60, 4, 'España', 'BEGINNER', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), (nextval('recipes_seq'), 'Tacos de Pescado y Mariscos', 'Preparación de los tacos...', 30, 2, 'México', 'BASIC', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Recipes Ingredients
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 1, 1, 200, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), (nextval('recipe_ingredients_seq'), 1, 2, 100, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), (nextval('recipe_ingredients_seq'), 2, 3, 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), (nextval('recipe_ingredients_seq'), 2, 4, 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Alternative Names
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(1, 'Rice'), (1, 'Reis'), (2, 'Chicken'), (2, 'Huhn'), (3, 'Tomato'), (3, 'Tomate'), (4, 'Tortilla'), (4, 'Tortilla');

CREATE INDEX idx_recipes_title_english ON recipes USING gin (to_tsvector('english', title));
CREATE INDEX idx_recipes_title_spanish ON recipes USING gin (to_tsvector('spanish', title));
