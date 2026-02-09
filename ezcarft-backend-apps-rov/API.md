# API Documentation

## Authentication Endpoints

### Register User
```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "username": "john.doe",
  "email": "john@example.com",
  "password": "SecurePass123!",
  "fullName": "John Doe"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "username": "john.doe",
  "email": "john@example.com"
}
```

### Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "john.doe",
  "password": "SecurePass123!"
}
```

---

## Catalog Endpoints

### List Products
```http
GET /api/v1/catalog/products?page=0&size=20&category=pottery
```

### Get Product Details
```http
GET /api/v1/catalog/products/{productId}
```

### Get Featured Products
```http
GET /api/v1/catalog/products/featured
```

---

## Cart Endpoints

### Get Cart
```http
GET /api/v1/cart
Cookie: CART_SESSION_ID=abc123
```

### Add Item to Cart
```http
POST /api/v1/cart/items
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}
```

---

## Order Endpoints

### Create Order
```http
POST /api/v1/orders
Authorization: Bearer {token}
Content-Type: application/json

{
  "items": [
    {
      "productId": 1,
      "quantity": 2,
      "price": 29.99
    }
  ]
}
```

### Get Orders
```http
GET /api/v1/orders
Authorization: Bearer {token}
```

---

## Payment Endpoints

### Create Payment Intent
```http
POST /api/v1/payments/orders/{orderId}/intent
Authorization: Bearer {token}
```

**Response:**
```json
{
  "clientSecret": "pi_xxx_secret_yyy",
  "orderId": 123,
  "amount": 5998
}
```
