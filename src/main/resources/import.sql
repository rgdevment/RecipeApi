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
INSERT INTO ingredients (id, name, created_at, updated_at) VALUES(nextval('ingredients_seq'), 'Arroz', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ingredients (id, name, created_at, updated_at) VALUES(nextval('ingredients_seq'), 'Pollo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ingredients (id, name, created_at, updated_at) VALUES(nextval('ingredients_seq'), 'Tomate', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ingredients (id, name, created_at, updated_at) VALUES(nextval('ingredients_seq'), 'Tortilla', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ingredients (id, name, created_at, updated_at) VALUES(nextval('ingredients_seq'), 'Pescado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ingredients (id, name, created_at, updated_at) VALUES(nextval('ingredients_seq'), 'Carne', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ingredients (id, name, created_at, updated_at) VALUES(nextval('ingredients_seq'), 'Lechuga', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ingredients (id, name, created_at, updated_at) VALUES(nextval('ingredients_seq'), 'Queso', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ingredients (id, name, created_at, updated_at) VALUES(nextval('ingredients_seq'), 'Pasta', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ingredients (id, name, created_at, updated_at) VALUES(nextval('ingredients_seq'), 'Pan', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ingredients (id, name, created_at, updated_at) VALUES(nextval('ingredients_seq'), 'Huevo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ingredients (id, name, created_at, updated_at) VALUES(nextval('ingredients_seq'), 'Leche', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ingredients (id, name, created_at, updated_at) VALUES(nextval('ingredients_seq'), 'Mantequilla', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ingredients (id, name, created_at, updated_at) VALUES(nextval('ingredients_seq'), 'Aceite', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Units Measures
INSERT INTO units_measures (id, name) VALUES(nextval('units_measures_seq'), 'Grams');
INSERT INTO units_measures (id, name) VALUES(nextval('units_measures_seq'), 'Cups');
INSERT INTO units_measures (id, name) VALUES(nextval('units_measures_seq'), 'Pieces');

-- Recipes (spanish)
INSERT INTO recipes (id, title, preparation, cooking_time, serving_size, country_adaptation, difficulty, user_id, created_at, updated_at) VALUES(nextval('recipes_seq'), 'Chupe de marisco al estilo puerto de Antofagasta', 'Preparación del chupe en Antofagasta...', 60, 4, 'Chile', 'BEGINNER', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipes (id, title, preparation, cooking_time, serving_size, country_adaptation, difficulty, user_id, created_at, updated_at) VALUES(nextval('recipes_seq'), 'Tacos de Pescado y Mariscos en un ambiente Queretano', 'Preparación de los tacos...', 30, 2, 'México', 'BASIC', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipes (id, title, preparation, cooking_time, serving_size, country_adaptation, difficulty, user_id, created_at, updated_at) VALUES(nextval('recipes_seq'), 'Ensalada de Lechuga y Tomate con Queso', 'Preparación de la ensalada...', 15, 2, 'Perú', 'BASIC', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipes (id, title, preparation, cooking_time, serving_size, country_adaptation, difficulty, user_id, created_at, updated_at) VALUES(nextval('recipes_seq'), 'Tortilla de Patatas sin patatas y sin tortilla', 'Preparación de la tortilla...', 30, 4, 'España', 'INTERMEDIATE', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- Recipes (English)
INSERT INTO recipes (id, title, preparation, cooking_time, serving_size, country_adaptation, difficulty, user_id, created_at, updated_at) VALUES(nextval('recipes_seq'), 'Fish and Chips', 'Preparation of Fish and Chips...', 45, 2, 'England', 'INTERMEDIATE', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipes (id, title, preparation, cooking_time, serving_size, country_adaptation, difficulty, user_id, created_at, updated_at) VALUES(nextval('recipes_seq'), 'Cheeseburger Vegan with chips', 'Preparation of Cheeseburger...', 30, 1, 'USA', 'BASIC', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipes (id, title, preparation, cooking_time, serving_size, country_adaptation, difficulty, user_id, created_at, updated_at) VALUES(nextval('recipes_seq'), 'Beef Tartare', 'Preparation of Beef Tartare...', 30, 2, 'Italia', 'INTERMEDIATE', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipes (id, title, preparation, cooking_time, serving_size, country_adaptation, difficulty, user_id, created_at, updated_at) VALUES(nextval('recipes_seq'), 'Sausage with bread', 'Preparation of Bratwurst mit Brot...', 15, 1, 'Deutschland', 'BASIC', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Recipes Ingredients
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 1, 1, 200, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 1, 2, 100, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 2, 3, 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 2, 4, 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 3, 7, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 3, 3, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 4, 10, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 4, 11, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 5, 5, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 5, 8, 100, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 6, 6, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 6, 10, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 7, 9, 200, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 7, 8, 50, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 8, 6, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit_measure_id, created_at, updated_at) VALUES(nextval('recipe_ingredients_seq'), 8, 10, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Alternative Names
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(1, 'Rice');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(1, 'Reis');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(2, 'Chicken');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(2, 'Huhn');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(3, 'Tomato');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(3, 'Tomate');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(4, 'Tortilla');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(4, 'Tortilla');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(5, 'Fish');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(5, 'Fisch');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(6, 'Meat');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(6, 'Fleisch');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(7, 'Lettuce');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(7, 'Salat');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(8, 'Cheese');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(8, 'Käse');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(9, 'Pasta');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(9, 'Nudeln');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(10, 'Bread');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(10, 'Brot');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(11, 'Egg');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(11, 'Ei');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(12, 'Milk');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(12, 'Milch');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(13, 'Butter');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(13, 'Butter');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(14, 'Oil');
INSERT INTO ingredient_alternate_names (ingredient_id, alternate_names) VALUES(14, 'Öl');
