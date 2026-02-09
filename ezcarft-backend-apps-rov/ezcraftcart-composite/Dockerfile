# Multi-stage Dockerfile for optimized production builds
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Copy parent POM and module POMs
COPY pom.xml .
COPY ezcraftcart-common/pom.xml ezcraftcart-common/
COPY ezcraftcart-catalog/pom.xml ezcraftcart-catalog/
COPY ezcraftcart-identity/pom.xml ezcraftcart-identity/
COPY ezcraftcart-cart/pom.xml ezcraftcart-cart/
COPY ezcraftcart-order/pom.xml ezcraftcart-order/
COPY ezcraftcart-cms/pom.xml ezcraftcart-cms/
COPY ezcraftcart-payment/pom.xml ezcraftcart-payment/
COPY ezcraftcart-inventory/pom.xml ezcraftcart-inventory/
COPY ezcraftcart-shipping/pom.xml ezcraftcart-shipping/
COPY ezcraftcart-notification/pom.xml ezcraftcart-notification/
COPY ezcraftcart-composite/pom.xml ezcraftcart-composite/

# Download dependencies
RUN apk add --no-cache maven && mvn dependency:go-offline -B

# Copy source code
COPY ezcraftcart-common/src ezcraftcart-common/src
COPY ezcraftcart-catalog/src ezcraftcart-catalog/src
COPY ezcraftcart-identity/src ezcraftcart-identity/src
COPY ezcraftcart-cart/src ezcraftcart-cart/src
COPY ezcraftcart-order/src ezcraftcart-order/src
COPY ezcraftcart-cms/src ezcraftcart-cms/src
COPY ezcraftcart-payment/src ezcraftcart-payment/src
COPY ezcraftcart-inventory/src ezcraftcart-inventory/src
COPY ezcraftcart-shipping/src ezcraftcart-shipping/src
COPY ezcraftcart-notification/src ezcraftcart-notification/src
COPY ezcraftcart-composite/src ezcraftcart-composite/src

# Build application
RUN mvn clean package -DskipTests -pl ezcraftcart-composite -am

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring

# Copy JAR from builder
COPY --from=builder /app/ezcraftcart-composite/target/*.jar app.jar

# Change ownership
RUN chown spring:spring app.jar

USER spring:spring

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

EXPOSE 8080

ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", \
  "app.jar"]
