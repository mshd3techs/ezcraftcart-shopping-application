# =============================================
# EzCraftCart Database Setup Script (PowerShell)
# =============================================

$PgPath = "C:\Program Files\PostgreSQL\18\bin\psql.exe"
$PgUser = "postgres"
$PgDatabase = "ezcraftcart_db"
$ScriptDir = $PSScriptRoot

Write-Host "=============================================" -ForegroundColor Cyan
Write-Host "EzCraftCart Database Initialization" -ForegroundColor Cyan
Write-Host "=============================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "This script will create and initialize the database"
Write-Host "You will be prompted for PostgreSQL password"
Write-Host ""

# Set password from environment or prompt
if (-not $env:PGPASSWORD) {
    $securePassword = Read-Host "Enter PostgreSQL password for user 'postgres'" -AsSecureString
    $BSTR = [System.Runtime.InteropServices.Marshal]::SecureStringToBSTR($securePassword)
    $env:PGPASSWORD = [System.Runtime.InteropServices.Marshal]::PtrToStringAuto($BSTR)
}

Write-Host ""
Write-Host "Step 1: Creating database..." -ForegroundColor Yellow
& $PgPath -U $PgUser -c "DROP DATABASE IF EXISTS ezcraftcart_db;" 2>&1 | Out-Null
& $PgPath -U $PgUser -c "CREATE DATABASE ezcraftcart_db;"

Write-Host "Step 2: Creating schema..." -ForegroundColor Yellow
& $PgPath -U $PgUser -d $PgDatabase -f "$ScriptDir\01-schema-ddl.sql"

Write-Host "Step 3: Inserting categories..." -ForegroundColor Yellow
& $PgPath -U $PgUser -d $PgDatabase -f "$ScriptDir\02-categories-data.sql"

Write-Host "Step 4: Inserting products..." -ForegroundColor Yellow
& $PgPath -U $PgUser -d $PgDatabase -f "$ScriptDir\03-products-data.sql"

Write-Host "Step 5: Inserting product details..." -ForegroundColor Yellow
& $PgPath -U $PgUser -d $PgDatabase -f "$ScriptDir\04-product-details-data.sql"

Write-Host ""
Write-Host "=============================================" -ForegroundColor Green
Write-Host "Verification" -ForegroundColor Green
Write-Host "=============================================" -ForegroundColor Green
& $PgPath -U $PgUser -d $PgDatabase -c "SELECT COUNT(*) as categories FROM categories; SELECT COUNT(*) as products FROM products; SELECT COUNT(*) as perfumes FROM products p JOIN categories c ON p.category_id = c.id WHERE c.slug = 'perfumes';"

Write-Host ""
Write-Host "=============================================" -ForegroundColor Green
Write-Host "Database initialization complete!" -ForegroundColor Green
Write-Host "=============================================" -ForegroundColor Green
Write-Host ""
Write-Host "Database: ezcraftcart_db" -ForegroundColor White
Write-Host "Categories: 7 (including Perfumes)" -ForegroundColor White
Write-Host "Products: 15 (including 3 perfume products)" -ForegroundColor White
Write-Host ""

# Clear password from environment
Remove-Item Env:\PGPASSWORD -ErrorAction SilentlyContinue
