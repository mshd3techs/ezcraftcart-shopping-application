-- =============================================
-- EzCraftCart Products Data Initialization
-- =============================================

BEGIN;

-- =============================================
-- CERAMIC PRODUCTS
-- =============================================
INSERT INTO products (id, name, description, price, original_price, category_id, subcategory, rating, review_count, 
                      artisan_name, artisan_location, artisan_avatar, in_stock, featured, trending) VALUES
('p1', 'Handcrafted Ceramic Bowl', 'Beautiful handmade ceramic bowl with unique glaze patterns', 45.00, 60.00, '1', 'Bowls', 4.8, 124, 
 'Sarah Potter', 'Portland, OR', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100&h=100&fit=crop', TRUE, TRUE, TRUE),
('p2', 'Artisan Tea Set', 'Complete handcrafted ceramic tea set with traditional designs', 89.00, NULL, '1', 'Tea Sets', 4.9, 87, 
 'Chen Wei', 'Jingdezhen, China', 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=100&h=100&fit=crop', TRUE, TRUE, FALSE);

-- =============================================
-- WOODEN PRODUCTS
-- =============================================
INSERT INTO products (id, name, description, price, original_price, category_id, subcategory, rating, review_count, 
                      artisan_name, artisan_location, artisan_avatar, in_stock, featured, trending) VALUES
('p3', 'Carved Wooden Sculpture', 'Intricate hand-carved wooden sculpture of traditional motifs', 120.00, 150.00, '2', 'Sculptures', 4.7, 156, 
 'John Carver', 'Vermont, USA', 'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=100&h=100&fit=crop', TRUE, FALSE, TRUE),
('p4', 'Walnut Cutting Board', 'Premium walnut cutting board with natural edge', 65.00, NULL, '2', 'Kitchen', 4.6, 203, 
 'Wood & Stone Co', 'Seattle, WA', 'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=100&h=100&fit=crop', TRUE, FALSE, FALSE);

-- =============================================
-- EARTH PRODUCTS
-- =============================================
INSERT INTO products (id, name, description, price, original_price, category_id, subcategory, rating, review_count, 
                      artisan_name, artisan_location, artisan_avatar, in_stock, featured, trending) VALUES
('p5', 'Clay Pottery Vase', 'Rustic clay vase with natural earth tones', 38.00, NULL, '3', 'Vases', 4.5, 89, 
 'Earth Artisans', 'Santa Fe, NM', 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=100&h=100&fit=crop', TRUE, FALSE, TRUE);

-- =============================================
-- SCENTED OILS PRODUCTS
-- =============================================
INSERT INTO products (id, name, description, price, original_price, category_id, subcategory, rating, review_count, 
                      artisan_name, artisan_location, artisan_avatar, in_stock, featured, trending) VALUES
('p6', 'Lavender Essential Oil', 'Pure lavender essential oil from Provence fields', 28.00, 35.00, '4', 'Essential Oils', 4.9, 312, 
 'Provence Oils', 'Grasse, France', 'https://images.unsplash.com/photo-1489424731084-a5d8b219a5bb?w=100&h=100&fit=crop', TRUE, TRUE, TRUE),
('p7', 'Sandalwood Blend', 'Premium sandalwood oil blend for aromatherapy', 42.00, NULL, '4', 'Blends', 4.7, 178, 
 'Mystic Aromas', 'Bangalore, India', 'https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=100&h=100&fit=crop', TRUE, FALSE, FALSE);

-- =============================================
-- METAL PRODUCTS
-- =============================================
INSERT INTO products (id, name, description, price, original_price, category_id, subcategory, rating, review_count, 
                      artisan_name, artisan_location, artisan_avatar, in_stock, featured, trending) VALUES
('p8', 'Bronze Statue', 'Hand-forged bronze statue with intricate details', 245.00, 300.00, '5', 'Statues', 4.8, 67, 
 'Metal Masters', 'Florence, Italy', 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=100&h=100&fit=crop', TRUE, TRUE, FALSE),
('p9', 'Copper Wall Art', 'Abstract copper wall art piece', 135.00, NULL, '5', 'Wall Art', 4.6, 94, 
 'Copper Craft Co', 'Austin, TX', 'https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=100&h=100&fit=crop', TRUE, FALSE, TRUE);

-- =============================================
-- POTS & PLANTERS PRODUCTS
-- =============================================
INSERT INTO products (id, name, description, price, original_price, category_id, subcategory, rating, review_count, 
                      artisan_name, artisan_location, artisan_avatar, in_stock, featured, trending) VALUES
('p10', 'Terracotta Planter Set', 'Set of 3 handcrafted terracotta planters', 55.00, 70.00, '6', 'Planters', 4.7, 189, 
 'Garden Artisans', 'Tuscany, Italy', 'https://images.unsplash.com/photo-1580489944761-15a19d654956?w=100&h=100&fit=crop', TRUE, FALSE, TRUE),
('p11', 'Ceramic Succulent Pot', 'Small decorative ceramic pot perfect for succulents', 18.00, NULL, '6', 'Small Pots', 4.5, 267, 
 'Plant & Pot', 'Portland, OR', 'https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=100&h=100&fit=crop', TRUE, FALSE, FALSE),
('p12', 'Large Garden Urn', 'Statement piece garden urn with classical design', 180.00, 220.00, '6', 'Urns', 4.8, 45, 
 'Classic Ceramics', 'Athens, Greece', 'https://images.unsplash.com/photo-1566275529824-cca6d008f3da?w=100&h=100&fit=crop', TRUE, TRUE, FALSE);

-- =============================================
-- PERFUMES PRODUCTS (NEW CATEGORY)
-- =============================================
INSERT INTO products (id, name, description, price, original_price, category_id, subcategory, rating, review_count, 
                      artisan_name, artisan_location, artisan_avatar, in_stock, featured, trending) VALUES
('p13', 'Artisan Oud Perfume', 'Luxurious handcrafted oud perfume with rich, woody notes. Made from aged agarwood and traditional distillation methods.', 185.00, 220.00, '7', 'Oud', 4.9, 156, 
 'Arabian Scents', 'Dubai, UAE', 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=100&h=100&fit=crop', TRUE, TRUE, TRUE),
('p14', 'Lavender Fields Eau de Parfum', 'Fresh and calming lavender perfume with hints of vanilla and musk. Handcrafted in Provence using organic lavender.', 98.00, NULL, '7', 'Floral', 4.7, 203, 
 'Provence Parfums', 'Grasse, France', 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=100&h=100&fit=crop', TRUE, FALSE, TRUE),
('p15', 'Rose Absolute Perfume Oil', 'Pure rose absolute perfume oil extracted from Damascus roses. Intense, long-lasting fragrance.', 145.00, NULL, '7', 'Floral', 4.8, 189, 
 'Rose Valley Distillery', 'Kazanlak, Bulgaria', 'https://images.unsplash.com/photo-1489424731084-a5d8b219a5bb?w=100&h=100&fit=crop', TRUE, TRUE, FALSE);

COMMIT;