# Getting Started Guide

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17 or higher** - [Download](https://adoptium.net/)
- **Maven 3.8+** - [Download](https://maven.apache.org/download.cgi)
- **Docker Desktop** - [Download](https://www.docker.com/products/docker-desktop)
- **Git** - [Download](https://git-scm.com/)

Optional:
- **PostgreSQL 15** - [Download](https://www.postgresql.org/download/)
- **Redis 7** - [Download](https://redis.io/download)

---

## Quick Start (5 Minutes)

### 1. Clone Repository

\\\ash
git clone https://github.com/mshd3techs/ezcraftcart-shopping-application.git
cd ezcraftcart-shopping-application/ezcarft-backend-apps-rovo
\\\

### 2. Start Infrastructure

\\\ash
docker-compose up -d
\\\

This starts:
- PostgreSQL (port 5432)
- Redis (port 6379)
- Kafka (port 9092)
- Prometheus (port 9090)
- Grafana (port 3000)

### 3. Build & Run

\\\ash
# Build all modules
mvn clean install

# Run application
cd ezcraftcart-composite
mvn spring-boot:run
\\\

### 4. Verify

Open browser to:
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health**: http://localhost:8080/actuator/health

---

## Development Workflow

### Project Structure

\\\
ezcarft-backend-apps-rovo/
├── ezcraftcart-common/          # Shared utilities
├── ezcraftcart-catalog/         # Product catalog
├── ezcraftcart-identity/        # Authentication
├── ezcraftcart-cart/            # Shopping cart
├── ezcraftcart-order/           # Order management
├── ezcraftcart-payment/         # Payment processing
├── ezcraftcart-cms/             # Headless CMS
├── ezcraftcart-inventory/       # Stock management
├── ezcraftcart-shipping/        # Fulfillment
├── ezcraftcart-notification/    # Email notifications
├── ezcraftcart-gateway/         # API Gateway
├── ezcraftcart-composite/       # Main application
├── kubernetes/                  # K8s configs
├── monitoring/                  # Prometheus configs
└── docker-compose.yml           # Docker setup
\\\

### Running Individual Services

Each module is a separate Maven project. To work on a specific service:

\\\ash
cd ezcraftcart-catalog
mvn spring-boot:run
\\\

### Hot Reload

Use Spring Boot DevTools for automatic restart:

\\\ash
mvn spring-boot:run -Dspring-boot.run.fork=false
\\\

---

## Configuration

### Application Profiles

**Development (default)**
- H2 in-memory database
- Debug logging
- No authentication required for testing

**Production**
\\\ash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
\\\

### Environment Variables

Create .env file:

\\\env
# Database
DB_HOST=localhost
DB_PASSWORD=ezcraftcart123

# Security
JWT_SECRET=your-super-secret-key-min-256-bits

# Stripe
STRIPE_API_KEY=sk_test_...
STRIPE_WEBHOOK_SECRET=whsec_...

# Email
MAIL_HOST=smtp.gmail.com
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=app-password
\\\

---

## Testing

### Run All Tests

\\\ash
mvn test
\\\

### Run Specific Module Tests

\\\ash
mvn test -pl ezcraftcart-catalog
\\\

### Integration Tests

\\\ash
mvn verify
\\\

### Test Coverage

\\\ash
mvn jacoco:report
# View: target/site/jacoco/index.html
\\\

---

## API Testing

### Register User

\\\ash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "Test123!",
    "fullName": "Test User"
  }'
\\\

### Login

\\\ash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "Test123!"
  }'
\\\

Save the token from response.

### Browse Products

\\\ash
curl http://localhost:8080/api/v1/catalog/products
\\\

### Create Order

\\\ash
curl -X POST http://localhost:8080/api/v1/orders \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "items": [
      {
        "productId": 1,
        "quantity": 2,
        "price": 29.99
      }
    ]
  }'
\\\

---

## Database Access

### H2 Console (Development)

http://localhost:8080/h2-console

- **JDBC URL**: jdbc:h2:mem:ezcraftcart
- **Username**: sa
- **Password**: (leave blank)

### PostgreSQL (Production)

\\\ash
psql -h localhost -U ezcraftcart -d ezcraftcart
\\\

---

## Monitoring

### Actuator Endpoints

- **Health**: http://localhost:8080/actuator/health
- **Metrics**: http://localhost:8080/actuator/metrics
- **Info**: http://localhost:8080/actuator/info

### Prometheus

http://localhost:9090

Query example: http_server_requests_seconds_count

### Grafana

http://localhost:3000
- Username: admin
- Password: admin

Import dashboard: Spring Boot 2.1 Statistics (ID: 11378)

---

## Troubleshooting

### Port Already in Use

\\\ash
# Kill process on port 8080
netstat -ano | findstr :8080
taskkill /PID <PID> /F
\\\

### Redis Connection Failed

\\\ash
# Check Redis is running
docker ps | grep redis

# Restart Redis
docker-compose restart redis
\\\

### Database Migration Issues

\\\ash
# Drop and recreate database
docker-compose down -v
docker-compose up -d postgres
\\\

### Build Failures

\\\ash
# Clean Maven cache
mvn clean install -U

# Skip tests
mvn clean install -DskipTests
\\\

---

## Next Steps

1. **Frontend Integration**: Connect Angular frontend from ezcraftcart-fontend-apps
2. **Payment Setup**: Configure Stripe account and webhooks
3. **Email Setup**: Configure SMTP for notifications
4. **Production Deploy**: Follow Kubernetes deployment guide

---

## Resources

- **Documentation**: See README.md
- **API Reference**: See API.md
- **Security Guide**: See SECURITY.md
- **Support**: https://github.com/mshd3techs/ezcraftcart-shopping-application/issues

---

**Happy Coding! 🚀**
