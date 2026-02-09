@echo off
REM EzCraftCart Backend Deployment Script for Windows

echo ==========================================
echo EzCraftCart Backend Deployment
echo ==========================================

set APP_NAME=ezcraftcart-backend
set VERSION=%date:~-4%%date:~-10,2%%date:~-7,2%-%time:~0,2%%time:~3,2%
set DOCKER_REGISTRY=your-registry.com

REM Check Java
echo [INFO] Checking prerequisites...
java -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Java is not installed
    exit /b 1
)

REM Check Maven
mvn -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Maven is not installed
    exit /b 1
)

echo [INFO] All prerequisites met

REM Parse deployment type
set DEPLOY_TYPE=%1
if "%DEPLOY_TYPE%"=="" set DEPLOY_TYPE=local

if "%DEPLOY_TYPE%"=="local" goto LOCAL
if "%DEPLOY_TYPE%"=="docker" goto DOCKER
if "%DEPLOY_TYPE%"=="k8s" goto K8S

echo [ERROR] Unknown deployment type: %DEPLOY_TYPE%
echo Usage: deploy.bat {local^|docker^|k8s}
exit /b 1

:LOCAL
echo [INFO] Local deployment
echo [INFO] Building application...
call mvn clean package -DskipTests
if errorlevel 1 (
    echo [ERROR] Build failed
    exit /b 1
)

echo [INFO] Running tests...
call mvn test
if errorlevel 1 (
    echo [ERROR] Tests failed
    exit /b 1
)

echo [INFO] Starting application locally...
cd ezcraftcart-composite
call mvn spring-boot:run
goto END

:DOCKER
echo [INFO] Docker deployment
echo [INFO] Building application...
call mvn clean package -DskipTests

echo [INFO] Building Docker image...
docker build -t %APP_NAME%:%VERSION% -f ezcraftcart-composite/Dockerfile .
docker tag %APP_NAME%:%VERSION% %APP_NAME%:latest

echo [INFO] Starting Docker containers...
docker-compose up -d

echo [INFO] Waiting for application to start...
timeout /t 30 /nobreak

echo [INFO] Health check...
curl -f http://localhost:8080/actuator/health
goto END

:K8S
echo [INFO] Kubernetes deployment
call mvn clean package -DskipTests

echo [INFO] Building Docker image...
docker build -t %APP_NAME%:%VERSION% -f ezcraftcart-composite/Dockerfile .

echo [INFO] Pushing to registry...
docker tag %APP_NAME%:%VERSION% %DOCKER_REGISTRY%/%APP_NAME%:%VERSION%
docker push %DOCKER_REGISTRY%/%APP_NAME%:%VERSION%

echo [INFO] Deploying to Kubernetes...
kubectl apply -f kubernetes/deployment.yaml
kubectl rollout status deployment/ezcraftcart-backend -n ezcraftcart

echo [INFO] Health check...
curl -f http://localhost:8080/actuator/health
goto END

:END
echo [INFO] Deployment completed successfully!
