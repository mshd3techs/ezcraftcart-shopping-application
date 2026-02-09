-- =============================================
-- EzCraftCart Database Initialization Script
-- Master Startup Script for Local Environment
-- =============================================
-- Database: ezcraftcart_db
-- User: ezcraftcart_user
-- Execute this script to initialize the complete database
-- =============================================

-- Create database (if needed)
-- Uncomment the following lines if database doesn't exist:
-- CREATE DATABASE ezcraftcart_db;
-- CREATE USER ezcraftcart_user WITH ENCRYPTED PASSWORD 'your_password_here';
-- GRANT ALL PRIVILEGES ON DATABASE ezcraftcart_db TO ezcraftcart_user;

-- Connect to database
\c ezcraftcart_db;

-- Execute schema creation
\i 01-schema-ddl.sql

-- Execute categories data
\i 02-categories-data.sql

-- Execute products data
\i 03-products-data.sql

-- Execute product details (images & tags)
\i 04-product-details-data.sql

-- Verify data
SELECT 'Categories Count: ' || COUNT(*) FROM categories;
SELECT 'Products Count: ' || COUNT(*) FROM products;
SELECT 'Product Images Count: ' || COUNT(*) FROM product_images;
SELECT 'Product Tags Count: ' || COUNT(*) FROM product_tags;

-- Show summary by category
SELECT 
    c.name as category,
    COUNT(p.id) as product_count,
    AVG(p.rating) as avg_rating,
    MIN(p.price) as min_price,
    MAX(p.price) as max_price
FROM categories c
LEFT JOIN products p ON c.id = p.category_id
GROUP BY c.id, c.name
ORDER BY c.id;

-- Show perfumes category details
SELECT 
    p.id,
    p.name,
    p.price,
    p.rating,
    p.review_count,
    p.artisan_name,
    p.featured,
    p.trending
FROM products p
JOIN categories c ON p.category_id = c.id
WHERE c.slug = 'perfumes'
ORDER BY p.id;

\echo '====================================='
\echo 'Database initialization complete!'
\echo '====================================='
\echo ''
\echo 'Categories: 7 (including Perfumes)'
\echo 'Products: 15 (including 3 perfume products)'
\echo ''
\echo 'Next steps:'
\echo '1. Update your application.properties with database credentials'
\echo '2. Start your Spring Boot application'
\echo '3. Access the API at http://localhost:8080'
\echo ''