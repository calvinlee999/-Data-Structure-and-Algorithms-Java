# Phase 7: Spring Cloud & Microservices - Quick Start

## 🚀 Start Everything with Docker Compose

```bash
# Build all services
docker-compose build

# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

## 📊 Service URLs

| Service | URL | Purpose |
|---------|-----|---------|
| **API Gateway** | http://localhost:8080 | Main entry point |
| **Service Registry** | http://localhost:8761 | Eureka dashboard |
| **Config Server** | http://localhost:8888 | Configuration |
| **Payment Service** | http://localhost:8081 | Payment processing |
| **User Service** | http://localhost:8082 | User management |
| **Account Service** | http://localhost:8083 | Account management |
| **Zipkin** | http://localhost:9411 | Distributed tracing |
| **Prometheus** | http://localhost:9090 | Metrics |
| **Grafana** | http://localhost:3000 | Dashboards (admin/admin) |

## 🧪 Test the System

```bash
# Health check
curl http://localhost:8080/actuator/health

# Process payment
curl -X POST http://localhost:8080/api/payments/process \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 100,
    "currency": "USD",
    "cardNumber": "1234567890123456",
    "cvv": "123",
    "expiryDate": "2026-12",
    "cardholderName": "John Doe"
  }'

# View trace in Zipkin
open http://localhost:9411
```

## 🎯 What You Get

✅ **6 Microservices** running in containers  
✅ **Service Discovery** (Eureka)  
✅ **API Gateway** (Spring Cloud Gateway)  
✅ **Distributed Tracing** (Zipkin)  
✅ **Metrics** (Prometheus + Grafana)  
✅ **Circuit Breakers** (Resilience4j)  
✅ **Virtual Threads** (Java 21)  
✅ **Sealed Interfaces** (Service contracts)

**Status**: Ready for evaluation! 🎉
