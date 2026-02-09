-- =============================================
-- EzCraftCart E-Commerce Database Schema
-- Local Development Environment Setup
-- Database: PostgreSQL 13+
-- =============================================

-- Drop existing tables if they exist (for clean restart)
DROP TABLE IF EXISTS product_tags CASCADE;
DROP TABLE IF EXISTS product_images CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS order_items CASCADE;

-- =============================================
-- 1. CATEGORIES TABLE
-- =============================================
CREATE TABLE categories (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    image VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_category_slug ON categories(slug);

-- =============================================
-- 2. PRODUCTS TABLE
-- =============================================
CREATE TABLE products (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    original_price DECIMAL(10, 2),
    category_id VARCHAR(36) NOT NULL,
    subcategory VARCHAR(100),
    rating DECIMAL(2, 1),
    review_count INTEGER DEFAULT 0,
    artisan_name VARCHAR(255),
    artisan_location VARCHAR(255),
    artisan_avatar VARCHAR(500),
    in_stock BOOLEAN DEFAULT TRUE,
    featured BOOLEAN DEFAULT FALSE,
    trending BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) 
        REFERENCES categories(id) ON DELETE CASCADE
);

CREATE INDEX idx_product_category ON products(category_id);
CREATE INDEX idx_product_featured ON products(featured);
CREATE INDEX idx_product_trending ON products(trending);
CREATE INDEX idx_product_price ON products(price);

-- =============================================
-- 3. PRODUCT_IMAGES TABLE
-- =============================================
CREATE TABLE product_images (
    id SERIAL PRIMARY KEY,
    product_id VARCHAR(36) NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    display_order INTEGER DEFAULT 0,
    CONSTRAINT fk_product_images FOREIGN KEY (product_id) 
        REFERENCES products(id) ON DELETE CASCADE
);

CREATE INDEX idx_product_images_product ON product_images(product_id);

-- =============================================
-- 4. PRODUCT_TAGS TABLE
-- =============================================
CREATE TABLE product_tags (
    id SERIAL PRIMARY KEY,
    product_id VARCHAR(36) NOT NULL,
    tag VARCHAR(50) NOT NULL,
    CONSTRAINT fk_product_tags FOREIGN KEY (product_id) 
        REFERENCES products(id) ON DELETE CASCADE
);

CREATE INDEX idx_product_tags_product ON product_tags(product_id);
CREATE INDEX idx_product_tags_tag ON product_tags(tag);

-- =============================================
-- 5. USERS TABLE (Basic structure)
-- =============================================
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    role VARCHAR(50) DEFAULT 'CUSTOMER',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_user_username ON users(username);

-- =============================================
-- Add updated_at trigger function
-- =============================================
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS \$\$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
\$\$ language 'plpgsql';

-- Apply triggers
CREATE TRIGGER update_categories_updated_at BEFORE UPDATE ON categories
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_products_updated_at BEFORE UPDATE ON products
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

COMMIT;