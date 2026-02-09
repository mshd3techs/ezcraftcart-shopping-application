@echo off
REM =============================================
REM EzCraftCart Database Setup Script
REM Execute this to initialize the database
REM =============================================

SET PGPATH="C:\Program Files\PostgreSQL\18\bin"
SET PGUSER=postgres
SET PGDATABASE=ezcraftcart_db
SET SCRIPTDIR=%~dp0

echo =============================================
echo EzCraftCart Database Initialization
echo =============================================
echo.
echo This script will:
echo 1. Create the database 'ezcraftcart_db'
echo 2. Create all tables and schemas
echo 3. Insert all data including Perfumes category
echo.
echo You will be prompted for PostgreSQL password
echo Default user: postgres
echo =============================================
echo.
pause

echo.
echo Step 1: Creating database...
echo.
%PGPATH%\psql -U %PGUSER% -c "DROP DATABASE IF EXISTS ezcraftcart_db;"
%PGPATH%\psql -U %PGUSER% -c "CREATE DATABASE ezcraftcart_db;"

echo.
echo Step 2: Creating schema...
echo.
%PGPATH%\psql -U %PGUSER% -d %PGDATABASE% -f "%SCRIPTDIR%01-schema-ddl.sql"

echo.
echo Step 3: Inserting categories...
echo.
%PGPATH%\psql -U %PGUSER% -d %PGDATABASE% -f "%SCRIPTDIR%02-categories-data.sql"

echo.
echo Step 4: Inserting products...
echo.
%PGPATH%\psql -U %PGUSER% -d %PGDATABASE% -f "%SCRIPTDIR%03-products-data.sql"

echo.
echo Step 5: Inserting product details...
echo.
%PGPATH%\psql -U %PGUSER% -d %PGDATABASE% -f "%SCRIPTDIR%04-product-details-data.sql"

echo.
echo =============================================
echo Verification
echo =============================================
echo.
%PGPATH%\psql -U %PGUSER% -d %PGDATABASE% -c "SELECT 'Categories: ' || COUNT(*) FROM categories; SELECT 'Products: ' || COUNT(*) FROM products; SELECT 'Perfume Products: ' || COUNT(*) FROM products p JOIN categories c ON p.category_id = c.id WHERE c.slug = 'perfumes';"

echo.
echo =============================================
echo Database initialization complete!
echo =============================================
echo.
echo Database: ezcraftcart_db
echo Categories: 7 (including Perfumes)
echo Products: 15 (including 3 perfume products)
echo.
pause