# EzCraftCart Enterprise E-Commerce Backend

## 🚀 Overview

Enterprise-grade e-commerce backend built with **Java 17**, **Spring Boot 3.2**, and microservices architecture for the EzCraftCart Handcrafted Artisan Marketplace.

### Key Features

✅ **Microservices Architecture** - Modular design with clear bounded contexts  
✅ **Enterprise Security** - JWT authentication, OAuth2, RBAC  
✅ **Payment Integration** - Stripe payment processing with webhooks  
✅ **Headless CMS** - Content management with versioning and workflows  
✅ **Real-time Inventory** - Pessimistic locking and reservation system  
✅ **Event-Driven** - Kafka for async communication  
✅ **Fault Tolerant** - Circuit breakers, retries, rate limiting  
✅ **Observability** - Prometheus metrics, distributed tracing  
✅ **Cloud-Native** - Docker and Kubernetes ready  
✅ **PCI-DSS Compliant** - Secure payment handling  

---

## 📦 Microservices

| Service | Port | Description |
|---------|------|-------------|
| **API Gateway** | 8081 | Spring Cloud Gateway with rate limiting |
| **Composite App** | 8080 | All services bundled (dev/demo) |
| **Catalog** | - | Products, categories, search |
| **Identity** | - | Authentication, JWT, users |
| **Cart** | - | Shopping cart with Redis |
| **Order** | - | Order management |
| **Payment** | - | Stripe integration |
| **Inventory** | - | Stock management with locking |
| **Shipping** | - | Fulfillment and tracking |
| **CMS** | - | Headless content management |
| **Notification** | - | Email notifications via Kafka |

---

## 🏗️ Architecture

\\\
┌─────────────┐
│   Frontend  │ (Angular)
└──────┬──────┘
       │
┌──────▼──────────────┐
│   API Gateway       │ (Rate Limiting, Circuit Breaker)
│   (Port 8081)       │
└──────┬──────────────┘
       │
┌──────▼──────────────────────────────────────────┐
│  Composite Application (Port 8080)              │
├─────────────┬────────────┬──────────┬──────────┤
│  Identity   │  Catalog   │   Cart   │  Order   │
│  Payment    │  Inventory │ Shipping │   CMS    │
└─────────────┴────────────┴──────────┴──────────┘
       │              │            │
┌──────▼──────┐ ┌────▼────┐ ┌─────▼─────┐
│ PostgreSQL  │ │  Redis  │ │   Kafka   │
└─────────────┘ └─────────┘ └───────────┘
\\\

---

## 🚦 Quick Start

### Prerequisites

- **Java 17+**
- **Maven 3.8+**
- **Docker** (optional)
- **PostgreSQL** (or use H2 for dev)
- **Redis**

### 1. Start Infrastructure (Docker)

\\\ash
docker-compose up -d
\\\

This starts:
- PostgreSQL (port 5432)
- Redis (port 6379)
- Kafka + Zookeeper
- Elasticsearch
- Prometheus + Grafana
- Jaeger

### 2. Build Application

\\\ash
mvn clean install
\\\

### 3. Run Application

**Option A: Composite (All services)**
\\\ash
cd ezcraftcart-composite
mvn spring-boot:run
\\\

**Option B: API Gateway + Composite**
\\\ash
# Terminal 1: Start composite
cd ezcraftcart-composite && mvn spring-boot:run

# Terminal 2: Start gateway
cd ezcraftcart-gateway && mvn spring-boot:run
\\\

### 4. Access Applications

- **API**: http://localhost:8080
- **Gateway**: http://localhost:8081
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Actuator**: http://localhost:8080/actuator
- **Grafana**: http://localhost:3000 (admin/admin)
- **Prometheus**: http://localhost:9090
- **Jaeger**: http://localhost:16686

---

## 🔐 Security

### Authentication Flow

1. **Register**: POST /api/v1/auth/register
2. **Login**: POST /api/v1/auth/login → Returns JWT token
3. **Use Token**: Add header Authorization: Bearer <token>

### JWT Configuration

\\\yaml
app:
  jwt:
    secret: \
    expiration-ms: 86400000  # 24 hours
\\\

### Protected Endpoints

- /api/v1/orders/** - Requires authentication
- /api/v1/cms/admin/** - Requires ADMIN role
- /api/v1/payments/** - Requires authentication

### Public Endpoints

- /api/v1/auth/** - Login, register
- /api/v1/catalog/** - Browse products
- /api/v1/cms/pages/** - Public CMS content

---

## 💳 Payment Integration

### Stripe Setup

1. Get API keys from [Stripe Dashboard](https://dashboard.stripe.com/apikeys)
2. Set environment variables:

\\\ash
export STRIPE_API_KEY=sk_test_...
export STRIPE_WEBHOOK_SECRET=whsec_...
\\\

### Payment Flow

1. **Create Order**: POST /api/v1/orders
2. **Create Payment Intent**: POST /api/v1/payments/orders/{orderId}/intent
3. **Frontend**: Use Stripe.js to collect card and confirm payment
4. **Webhook**: Stripe notifies /api/v1/webhooks/stripe
5. **Order Status**: Updated to CONFIRMED on success

### Testing

Use Stripe test cards:
- Success: 4242 4242 4242 4242
- Decline: 4000 0000 0000 0002

---

## 📊 Monitoring & Observability

### Metrics (Prometheus)

\\\ash
curl http://localhost:8080/actuator/prometheus
\\\

### Health Checks

\\\ash
curl http://localhost:8080/actuator/health
\\\

### Distributed Tracing (Jaeger)

Access Jaeger UI at http://localhost:16686

### Circuit Breakers

Configured with Resilience4j:
- Inventory service
- Payment service
- External API calls

---

## 🗄️ Database

### H2 (Development)

\\\yaml
spring:
  datasource:
    url: jdbc:h2:mem:ezcraftcart
  jpa:
    hibernate:
      ddl-auto: create-drop
\\\

### PostgreSQL (Production)

\\\yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ezcraftcart
    username: ezcraftcart
    password: \
  jpa:
    hibernate:
      ddl-auto: validate  # Use Flyway/Liquibase
\\\

---

## 🐳 Docker Deployment

### Build Image

\\\ash
docker build -t ezcraftcart/backend:latest -f ezcraftcart-composite/Dockerfile .
\\\

### Run Container

\\\ash
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DB_PASSWORD=secret \
  -e JWT_SECRET=your-secret \
  ezcraftcart/backend:latest
\\\

---

## ☸️ Kubernetes Deployment

### Deploy to K8s

\\\ash
kubectl apply -f kubernetes/deployment.yaml
\\\

### Scale Application

\\\ash
kubectl scale deployment ezcraftcart-backend --replicas=5 -n ezcraftcart
\\\

### Check Status

\\\ash
kubectl get pods -n ezcraftcart
kubectl logs -f deployment/ezcraftcart-backend -n ezcraftcart
\\\

---

## 🧪 Testing

### Unit Tests

\\\ash
mvn test
\\\

### Integration Tests

\\\ash
mvn verify
\\\

### Load Testing (Gatling)

\\\ash
mvn gatling:test
\\\

---

## 📝 API Documentation

### OpenAPI/Swagger

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### Sample Requests

**Register User**
\\\ash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john.doe",
    "email": "john@example.com",
    "password": "SecurePass123",
    "fullName": "John Doe"
  }'
\\\

**Get Products**
\\\ash
curl http://localhost:8080/api/v1/catalog/products
\\\

**Add to Cart**
\\\ash
curl -X POST http://localhost:8080/api/v1/cart/items \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "quantity": 2
  }'
\\\

---

## 🔧 Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| SPRING_PROFILES_ACTIVE | Active profile (dev/prod) | dev |
| DB_PASSWORD | Database password | - |
| JWT_SECRET | JWT signing secret | (dev default) |
| STRIPE_API_KEY | Stripe API key | - |
| STRIPE_WEBHOOK_SECRET | Stripe webhook secret | - |
| REDIS_HOST | Redis host | localhost |
| KAFKA_BOOTSTRAP_SERVERS | Kafka servers | localhost:9092 |

### Profiles

- **dev**: H2 database, debug logging
- **prod**: PostgreSQL, optimized settings

---

## 🛡️ Compliance

### PCI-DSS

- ✅ No card data stored on server
- ✅ Stripe handles tokenization
- ✅ HTTPS enforced in production
- ✅ Webhook signature validation

### GDPR

- ✅ User data encryption at rest
- ✅ Personal data deletion support
- ✅ Audit logging
- ✅ Data export capabilities

---

## 🤝 Contributing

1. Fork the repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request

---

## 📄 License

MIT License - See LICENSE file

---

## 🆘 Support

- **Email**: support@ezcraftcart.com
- **Docs**: https://docs.ezcraftcart.com
- **Issues**: https://github.com/mshd3techs/ezcraftcart-shopping-application/issues

---

## 🗺️ Roadmap

- [ ] Elasticsearch integration for product search
- [ ] Multi-tenant support
- [ ] GraphQL API
- [ ] Mobile app backend
- [ ] Advanced analytics
- [ ] Recommendation engine
- [ ] Internationalization (i18n)
- [ ] Multi-currency support

---

**Built with ❤️ for artisans and craftspeople**
