CREATE INDEX idx_recipes_title_english ON recipes USING gin (to_tsvector('english', title));
CREATE INDEX idx_recipes_title_spanish ON recipes USING gin (to_tsvector('spanish', title));
