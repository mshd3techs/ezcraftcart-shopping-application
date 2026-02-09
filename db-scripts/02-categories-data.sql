-- =============================================
-- EzCraftCart Categories Data Initialization
-- =============================================

BEGIN;

-- Clear existing data
DELETE FROM product_tags;
DELETE FROM product_images;
DELETE FROM products;
DELETE FROM categories;

-- Insert Categories
INSERT INTO categories (id, name, slug, description, image) VALUES
('1', 'Ceramic', 'ceramic', 'Handcrafted ceramic art and pottery', 'https://images.unsplash.com/photo-1610701596007-11502861dcfa?w=600&h=400&fit=crop'),
('2', 'Wooden', 'wooden', 'Artisan wooden crafts and furniture', 'https://images.unsplash.com/photo-1615529182904-14819c35db37?w=600&h=400&fit=crop'),
('3', 'Earth', 'earth', 'Natural earth-based crafts', 'https://images.unsplash.com/photo-1615719413546-198b25453f43?w=600&h=400&fit=crop'),
('4', 'Scented Oils', 'scented-oils', 'Aromatic essential and scented oils', 'https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?w=600&h=400&fit=crop'),
('5', 'Metal', 'metal', 'Handcrafted metal art and sculptures', 'https://images.unsplash.com/photo-1610807547618-ca5e49e64c5c?w=600&h=400&fit=crop'),
('6', 'Pots & Planters', 'pots', 'Decorative pots and planters', 'https://images.unsplash.com/photo-1485955900006-10f4d324d411?w=600&h=400&fit=crop'),
('7', 'Perfumes', 'perfumes', 'Artisan fragrances and luxury perfumes', 'https://images.unsplash.com/photo-1541643600914-78b084683601?w=600&h=400&fit=crop');

COMMIT;