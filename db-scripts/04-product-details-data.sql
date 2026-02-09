-- =============================================
-- EzCraftCart Product Images & Tags Data
-- =============================================

BEGIN;

-- =============================================
-- PRODUCT IMAGES
-- =============================================

-- Ceramic Bowl (p1)
INSERT INTO product_images (product_id, image_url, display_order) VALUES
('p1', 'https://images.unsplash.com/photo-1578749556568-bc2c40e68b61?w=600&h=600&fit=crop', 1),
('p1', 'https://images.unsplash.com/photo-1610701596007-11502861dcfa?w=600&h=600&fit=crop', 2);

-- Artisan Tea Set (p2)
INSERT INTO product_images (product_id, image_url, display_order) VALUES
('p2', 'https://images.unsplash.com/photo-1564890369478-c89ca6d9cde9?w=600&h=600&fit=crop', 1);

-- Carved Wooden Sculpture (p3)
INSERT INTO product_images (product_id, image_url, display_order) VALUES
('p3', 'https://images.unsplash.com/photo-1615529182904-14819c35db37?w=600&h=600&fit=crop', 1);

-- Lavender Essential Oil (p6)
INSERT INTO product_images (product_id, image_url, display_order) VALUES
('p6', 'https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?w=600&h=600&fit=crop', 1);

-- Bronze Statue (p8)
INSERT INTO product_images (product_id, image_url, display_order) VALUES
('p8', 'https://images.unsplash.com/photo-1610807547618-ca5e49e64c5c?w=600&h=600&fit=crop', 1);

-- Terracotta Planter Set (p10)
INSERT INTO product_images (product_id, image_url, display_order) VALUES
('p10', 'https://images.unsplash.com/photo-1485955900006-10f4d324d411?w=600&h=600&fit=crop', 1);

-- Large Garden Urn (p12)
INSERT INTO product_images (product_id, image_url, display_order) VALUES
('p12', 'https://images.unsplash.com/photo-1566275529824-cca6d008f3da?w=600&h=600&fit=crop', 1);

-- PERFUME PRODUCTS IMAGES
-- Artisan Oud Perfume (p13)
INSERT INTO product_images (product_id, image_url, display_order) VALUES
('p13', 'https://images.unsplash.com/photo-1592945403244-b3fbafd7f539?w=600&h=600&fit=crop', 1),
('p13', 'https://images.unsplash.com/photo-1541643600914-78b084683601?w=600&h=600&fit=crop', 2);

-- Lavender Fields Eau de Parfum (p14)
INSERT INTO product_images (product_id, image_url, display_order) VALUES
('p14', 'https://images.unsplash.com/photo-1588405748880-12d1d2a59d75?w=600&h=600&fit=crop', 1);

-- Rose Absolute Perfume Oil (p15)
INSERT INTO product_images (product_id, image_url, display_order) VALUES
('p15', 'https://images.unsplash.com/photo-1587017539504-67cfbddac569?w=600&h=600&fit=crop', 1);

-- =============================================
-- PRODUCT TAGS
-- =============================================

-- Ceramic Bowl (p1)
INSERT INTO product_tags (product_id, tag) VALUES
('p1', 'ceramic'), ('p1', 'bowl'), ('p1', 'handmade'), ('p1', 'artisan');

-- Artisan Tea Set (p2)
INSERT INTO product_tags (product_id, tag) VALUES
('p2', 'ceramic'), ('p2', 'tea'), ('p2', 'traditional'), ('p2', 'gift');

-- Carved Wooden Sculpture (p3)
INSERT INTO product_tags (product_id, tag) VALUES
('p3', 'wood'), ('p3', 'sculpture'), ('p3', 'carved'), ('p3', 'decor');

-- Walnut Cutting Board (p4)
INSERT INTO product_tags (product_id, tag) VALUES
('p4', 'wood'), ('p4', 'kitchen'), ('p4', 'walnut'), ('p4', 'functional');

-- Clay Pottery Vase (p5)
INSERT INTO product_tags (product_id, tag) VALUES
('p5', 'clay'), ('p5', 'pottery'), ('p5', 'vase'), ('p5', 'rustic');

-- Lavender Essential Oil (p6)
INSERT INTO product_tags (product_id, tag) VALUES
('p6', 'essential-oil'), ('p6', 'lavender'), ('p6', 'aromatherapy'), ('p6', 'natural');

-- Sandalwood Blend (p7)
INSERT INTO product_tags (product_id, tag) VALUES
('p7', 'essential-oil'), ('p7', 'sandalwood'), ('p7', 'aromatherapy'), ('p7', 'blend');

-- Bronze Statue (p8)
INSERT INTO product_tags (product_id, tag) VALUES
('p8', 'metal'), ('p8', 'bronze'), ('p8', 'statue'), ('p8', 'art');

-- Copper Wall Art (p9)
INSERT INTO product_tags (product_id, tag) VALUES
('p9', 'metal'), ('p9', 'copper'), ('p9', 'wall-art'), ('p9', 'modern');

-- Terracotta Planter Set (p10)
INSERT INTO product_tags (product_id, tag) VALUES
('p10', 'terracotta'), ('p10', 'planter'), ('p10', 'garden'), ('p10', 'set');

-- Ceramic Succulent Pot (p11)
INSERT INTO product_tags (product_id, tag) VALUES
('p11', 'ceramic'), ('p11', 'pot'), ('p11', 'succulent'), ('p11', 'small');

-- Large Garden Urn (p12)
INSERT INTO product_tags (product_id, tag) VALUES
('p12', 'ceramic'), ('p12', 'urn'), ('p12', 'garden'), ('p12', 'large');

-- PERFUME PRODUCT TAGS
-- Artisan Oud Perfume (p13)
INSERT INTO product_tags (product_id, tag) VALUES
('p13', 'perfume'), ('p13', 'oud'), ('p13', 'luxury'), ('p13', 'woody');

-- Lavender Fields Eau de Parfum (p14)
INSERT INTO product_tags (product_id, tag) VALUES
('p14', 'perfume'), ('p14', 'lavender'), ('p14', 'floral'), ('p14', 'organic');

-- Rose Absolute Perfume Oil (p15)
INSERT INTO product_tags (product_id, tag) VALUES
('p15', 'perfume'), ('p15', 'rose'), ('p15', 'oil'), ('p15', 'natural');

COMMIT;