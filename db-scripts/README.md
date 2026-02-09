# EzCraftCart Database Initialization Scripts

## Overview
Complete database setup scripts for EzCraftCart e-commerce application in local development environment.

## Database Requirements
- **Database:** PostgreSQL 13 or higher
- **Database Name:** ezcraftcart_db
- **User:** ezcraftcart_user
- **Password:** Configure as needed

## Files Included

### 1. 00-master-startup.sql
**Master script** - Executes all other scripts in order. Use this for quick setup.

### 2. 01-schema-ddl.sql
Creates all database tables and indexes:
- categories
- products
- product_images
- product_tags
- users (basic structure)
- Triggers for auto-updating timestamps

### 3. 02-categories-data.sql
Inserts 7 product categories:
1. Ceramic
2. Wooden
3. Earth
4. Scented Oils
5. Metal
6. Pots & Planters
7. **Perfumes** (NEW)

### 4. 03-products-data.sql
Inserts 15 products across all categories:
- 12 existing products (ceramic, wooden, earth, oils, metal, pots)
- **3 NEW perfume products:**
  - p13: Artisan Oud Perfume (\)
  - p14: Lavender Fields Eau de Parfum (\)
  - p15: Rose Absolute Perfume Oil (\)

### 5. 04-product-details-data.sql
Inserts product images and tags for all products including perfumes.

## Quick Start

### Option 1: Using Master Script (Recommended)
\\\ash
# From PostgreSQL command line
psql -U postgres

# Then execute:
\i /path/to/db-scripts/00-master-startup.sql
\\\

### Option 2: Using psql Command Line
\\\ash
psql -U postgres -d ezcraftcart_db -f 00-master-startup.sql
\\\

### Option 3: Execute Scripts Individually
\\\ash
psql -U postgres -d ezcraftcart_db -f 01-schema-ddl.sql
psql -U postgres -d ezcraftcart_db -f 02-categories-data.sql
psql -U postgres -d ezcraftcart_db -f 03-products-data.sql
psql -U postgres -d ezcraftcart_db -f 04-product-details-data.sql
\\\

### Option 4: Using pgAdmin or DBeaver
1. Connect to your PostgreSQL server
2. Create database 'ezcraftcart_db' (if not exists)
3. Execute each SQL file in order (00, 01, 02, 03, 04)

## Database Setup Steps

### 1. Create Database and User
\\\sql
CREATE DATABASE ezcraftcart_db;
CREATE USER ezcraftcart_user WITH ENCRYPTED PASSWORD 'your_secure_password';
GRANT ALL PRIVILEGES ON DATABASE ezcraftcart_db TO ezcraftcart_user;
\\\

### 2. Execute Master Script
\\\ash
psql -U postgres -d ezcraftcart_db -f 00-master-startup.sql
\\\

### 3. Verify Installation
\\\sql
-- Check record counts
SELECT COUNT(*) FROM categories;  -- Should return 7
SELECT COUNT(*) FROM products;    -- Should return 15
SELECT COUNT(*) FROM product_images; -- Should return 11
SELECT COUNT(*) FROM product_tags;   -- Should return 42

-- View perfumes category
SELECT * FROM categories WHERE slug = 'perfumes';

-- View perfume products
SELECT p.name, p.price, p.rating, p.artisan_name 
FROM products p 
JOIN categories c ON p.category_id = c.id 
WHERE c.slug = 'perfumes';
\\\

## Application Configuration

Update your Spring Boot application.properties:

\\\properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/ezcraftcart_db
spring.datasource.username=ezcraftcart_user
spring.datasource.password=your_secure_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Logging
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
\\\

## Data Summary

### Categories (7 total)
| ID | Name | Slug | Products |
|----|------|------|----------|
| 1 | Ceramic | ceramic | 2 |
| 2 | Wooden | wooden | 2 |
| 3 | Earth | earth | 1 |
| 4 | Scented Oils | scented-oils | 2 |
| 5 | Metal | metal | 2 |
| 6 | Pots & Planters | pots | 3 |
| 7 | **Perfumes** | perfumes | **3** |

### Perfumes Products (NEW)

#### p13 - Artisan Oud Perfume
- **Price:** \.00 (was \.00)
- **Rating:** 4.9 ⭐ (156 reviews)
- **Artisan:** Arabian Scents, Dubai
- **Tags:** perfume, oud, luxury, woody
- **Featured:** ✅ | **Trending:** ✅

#### p14 - Lavender Fields Eau de Parfum
- **Price:** \.00
- **Rating:** 4.7 ⭐ (203 reviews)
- **Artisan:** Provence Parfums, France
- **Tags:** perfume, lavender, floral, organic
- **Trending:** ✅

#### p15 - Rose Absolute Perfume Oil
- **Price:** \.00
- **Rating:** 4.8 ⭐ (189 reviews)
- **Artisan:** Rose Valley Distillery, Bulgaria
- **Tags:** perfume, rose, oil, natural
- **Featured:** ✅

## Troubleshooting

### Connection Issues
\\\ash
# Check PostgreSQL is running
sudo systemctl status postgresql

# Start PostgreSQL
sudo systemctl start postgresql
\\\

### Permission Errors
\\\sql
-- Grant necessary permissions
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO ezcraftcart_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO ezcraftcart_user;
\\\

### Reset Database
\\\sql
-- Drop and recreate database for clean start
DROP DATABASE IF EXISTS ezcraftcart_db;
CREATE DATABASE ezcraftcart_db;
-- Then run master startup script again
\\\

## Testing the Setup

### API Endpoints to Test
\\\ash
# Get all categories (should return 7 including Perfumes)
curl http://localhost:8080/api/v1/categories

# Get perfumes category
curl http://localhost:8080/api/v1/categories/perfumes

# Get all perfume products
curl http://localhost:8080/api/v1/products?category=perfumes

# Get specific perfume product
curl http://localhost:8080/api/v1/products/p13
\\\

## Notes
- All scripts use UTF-8 encoding
- Transactions are used for data consistency
- Foreign key constraints ensure referential integrity
- Indexes are created for optimal query performance
- All timestamps auto-update on record modification

## Support
For issues or questions, refer to the backend application documentation.

---
**Created:** 2026-02-08  
**Database Version:** PostgreSQL 13+  
**Application:** EzCraftCart E-Commerce Platform