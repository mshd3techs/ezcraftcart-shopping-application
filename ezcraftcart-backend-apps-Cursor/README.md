# EzCraftCart Backend

Enterprise-grade e-commerce backend for the EzCraftCart Handcrafted Artisan Marketplace.

## Architecture

- **Modular monolith** with clear bounded contexts, ready to split into microservices
- **Java 17** + **Spring Boot 3.2**
- Aligned with [enterprise implementation plan](https://github.com/mshd3techs/ezcraftcart-shopping-application/blob/main/Docs/enterprise_e_commerce_microservices_backend_full_OPEN_SOURCE-implementation_plan.md)

### Modules

| Module | Responsibilities |
|--------|------------------|
| **ezcraftcart-common** | Shared exceptions, utilities |
| **ezcraftcart-catalog** | Products, categories, search, caching (Redis) |
| **ezcraftcart-identity** | Auth, JWT, users, RBAC |
| **ezcraftcart-cart** | Session carts (Redis) |
| **ezcraftcart-order** | Order lifecycle |
| **ezcraftcart-cms** | Headless CMS – pages, banners, content blocks |
| **ezcraftcart-payment** | Stripe payment intents, webhooks |
| **ezcraftcart-composite** | Main application, security, observability |

### API Endpoints

| Service | Path | Description |
|---------|------|-------------|
| Catalog | `/api/v1/catalog/products` | List products with filters |
| Catalog | `/api/v1/catalog/products/{id}` | Get product by ID |
| Catalog | `/api/v1/catalog/products/featured` | Featured products |
| Catalog | `/api/v1/catalog/products/trending` | Trending products |
| Catalog | `/api/v1/catalog/categories` | List categories |
| Auth | `/api/v1/auth/login` | Login |
| Auth | `/api/v1/auth/register` | Register |
| Cart | `/api/v1/cart` | Get/clear cart |
| Cart | `/api/v1/cart/items` | Add/update/remove items |
| Orders | `/api/v1/orders` | Create, list orders |
| CMS (public) | `/api/v1/cms/pages/{slug}` | Get published page |
| CMS (public) | `/api/v1/cms/banners` | Get published banners |
| CMS (public) | `/api/v1/cms/blocks/{key}` | Get published content block |
| CMS (admin) | `/api/v1/cms/admin/**` | CRUD, publish, rollback (requires ADMIN) |
| Payment | `POST /api/v1/payments/orders/{orderId}/intent` | Create Stripe payment intent |
| Payment | `GET /api/v1/payments/orders/{orderId}` | Get payment status |
| Webhook | `POST /api/v1/webhooks/stripe` | Stripe webhook (payment_intent.succeeded/failed) |

## Quick Start

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker (optional, for Redis/PostgreSQL)

### Run with H2 + Redis

1. Start Redis:
   ```bash
   docker run -d -p 6379:6379 redis:7-alpine
   ```

2. Build and run:
   ```bash
   mvn clean install
   cd ezcraftcart-composite && mvn spring-boot:run
   ```

3. Access:
   - API: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - Actuator: http://localhost:8080/actuator/health

### Run with Docker Compose

```bash
docker-compose up -d
# Set spring.profiles.active=prod and DB_HOST=localhost
mvn -pl ezcraftcart-composite spring-boot:run -Dspring-boot.run.profiles=prod
```

## Configuration

| Property | Default | Description |
|----------|---------|-------------|
| `spring.datasource.url` | H2 in-memory | Database URL |
| `spring.data.redis.host` | localhost | Redis host |
| `app.jwt.secret` | (dev default) | JWT signing secret |
| `app.jwt.expiration-ms` | 86400000 | Token expiry (24h) |
| `stripe.api-key` | - | Stripe API key (required for payments) |
| `stripe.webhook-secret` | - | Stripe webhook signing secret |

## Security

- **JWT** (RS256-style HMAC) for stateless auth
- **CORS** configured for Angular frontend (localhost:4200)
- **Public**: `/api/v1/auth/**`, `/api/v1/catalog/**`, `/api/v1/cart/**`
- **Protected**: `/api/v1/orders/**` (requires Bearer token)

## Frontend Integration

Configure the [EzCraftCart Angular frontend](https://github.com/mshd3techs/ezcraftcart-shopping-application/tree/main/ezcraftcart-fontend-apps) to point to:

```
API_BASE_URL=http://localhost:8080/api/v1
```

## Payment Flow

1. **Create order** → `POST /api/v1/orders` (status: PENDING_PAYMENT)
2. **Create payment intent** → `POST /api/v1/payments/orders/{orderId}/intent`
3. **Frontend** uses `clientSecret` with [Stripe.js](https://stripe.com/docs/js) / Elements
4. **Webhook** `payment_intent.succeeded` → Order status set to CONFIRMED

**PCI-DSS**: Card data never touches the server; Stripe handles tokenization.

## License

Open source – see project root.
