#!/bin/bash

# EzCraftCart Backend Deployment Script

set -e

echo "=========================================="
echo "EzCraftCart Backend Deployment"
echo "=========================================="

# Configuration
APP_NAME="ezcraftcart-backend"
VERSION=$(date +%Y%m%d-%H%M%S)
DOCKER_REGISTRY="your-registry.com"

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

# Functions
log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check prerequisites
check_prerequisites() {
    log_info "Checking prerequisites..."
    
    if ! command -v java &> /dev/null; then
        log_error "Java is not installed"
        exit 1
    fi
    
    if ! command -v mvn &> /dev/null; then
        log_error "Maven is not installed"
        exit 1
    fi
    
    if ! command -v docker &> /dev/null; then
        log_error "Docker is not installed"
        exit 1
    fi
    
    log_info "All prerequisites met"
}

# Build application
build_app() {
    log_info "Building application..."
    mvn clean package -DskipTests
    log_info "Build completed successfully"
}

# Run tests
run_tests() {
    log_info "Running tests..."
    mvn test
    log_info "Tests passed"
}

# Build Docker image
build_docker() {
    log_info "Building Docker image..."
    docker build -t ${APP_NAME}:${VERSION} -f ezcraftcart-composite/Dockerfile .
    docker tag ${APP_NAME}:${VERSION} ${APP_NAME}:latest
    log_info "Docker image built: ${APP_NAME}:${VERSION}"
}

# Push to registry
push_docker() {
    log_info "Pushing to Docker registry..."
    docker tag ${APP_NAME}:${VERSION} ${DOCKER_REGISTRY}/${APP_NAME}:${VERSION}
    docker push ${DOCKER_REGISTRY}/${APP_NAME}:${VERSION}
    log_info "Image pushed to registry"
}

# Deploy to Kubernetes
deploy_k8s() {
    log_info "Deploying to Kubernetes..."
    kubectl apply -f kubernetes/deployment.yaml
    kubectl rollout status deployment/ezcraftcart-backend -n ezcraftcart
    log_info "Deployment completed"
}

# Health check
health_check() {
    log_info "Performing health check..."
    sleep 10
    
    HEALTH_URL="http://localhost:8080/actuator/health"
    if curl -f ${HEALTH_URL} > /dev/null 2>&1; then
        log_info "Health check passed"
    else
        log_error "Health check failed"
        exit 1
    fi
}

# Main deployment flow
main() {
    check_prerequisites
    
    case "${1:-local}" in
        local)
            log_info "Local deployment"
            build_app
            run_tests
            log_info "Starting application locally..."
            cd ezcraftcart-composite && mvn spring-boot:run
            ;;
        docker)
            log_info "Docker deployment"
            build_app
            build_docker
            log_info "Starting Docker container..."
            docker-compose up -d
            health_check
            ;;
        k8s|kubernetes)
            log_info "Kubernetes deployment"
            build_app
            run_tests
            build_docker
            push_docker
            deploy_k8s
            health_check
            ;;
        *)
            log_error "Unknown deployment type: $1"
            echo "Usage: $0 {local|docker|k8s}"
            exit 1
            ;;
    esac
    
    log_info "Deployment completed successfully!"
}

main "$@"
