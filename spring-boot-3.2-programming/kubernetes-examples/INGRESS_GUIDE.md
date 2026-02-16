# Kubernetes Ingress Configuration
# External access and TLS termination for microservices

**Production-ready Ingress configuration for FinTech microservices on Kubernetes**

---

## What is Ingress?

Think of Ingress as a **smart router** that sits at the edge of your Kubernetes cluster.

```
Internet → Ingress Controller → Service → Pods
```

**Without Ingress:**
- Each service needs its own LoadBalancer (expensive!)
- No path-based routing
- TLS configuration per service

**With Ingress:**
- ✅ Single LoadBalancer
- ✅ Path-based routing
- ✅ Centralized TLS termination
- ✅ Virtual hosting (multiple domains)
- ✅ Rate limiting & authentication

---

## Install NGINX Ingress Controller

```bash
# Using Helm
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update

# Install  in ingress-nginx namespace
helm install ingress-nginx ingress-nginx/ingress-nginx \
  --namespace ingress-nginx \
  --create-namespace \
  --set controller.replicaCount=3 \
  --set controller.metrics.enabled=true \
  --set controller.podAnnotations."prometheus\.io/scrape"=true

# Verify installation
kubectl get pods -n ingress-nginx
kubectl get svc -n ingress-nginx
```

---

## Basic Ingress Resource

```yaml
# transaction-service-ingress.yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: fintech-ingress
  namespace: fintech
  annotations:
    # Use NGINX ingress controller
    kubernetes.io/ingress.class: "nginx"
    
    # Rewrite paths
    nginx.ingress.kubernetes.io/rewrite-target: /$2
    
    # CORS configuration
    nginx.ingress.kubernetes.io/enable-cors: "true"
    nginx.ingress.kubernetes.io/cors-allow-methods: "GET, POST, PUT, DELETE, OPTIONS"
    nginx.ingress.kubernetes.io/cors-allow-origin: "https://app.fintech.com"
    nginx.ingress.kubernetes.io/cors-allow-credentials: "true"
    
    # Rate limiting (10 requests per second per IP)
    nginx.ingress.kubernetes.io/limit-rps: "10"
    
    # Connection limits
    nginx.ingress.kubernetes.io/limit-connections: "20"
spec:
  rules:
  # Route based on hostname
  - host: api.fintech.com
    http:
      paths:
      # Transaction Service
      - path: /api/v1/transactions(/|$)(.*)
        pathType: ImplementationSpecific
        backend:
          service:
            name: transaction-service
            port:
              number: 80
      
      # Customer Service
      - path: /api/v1/customers(/|$)(.*)
        pathType: ImplementationSpecific
        backend:
          service:
            name: customer-service
            port:
              number: 80
      
      # Account Service
      - path: /api/v1/accounts(/|$)(.*)
        pathType: ImplementationSpecific
        backend:
          service:
            name: account-service
            port:
              number: 80
```

---

## TLS/HTTPS Configuration

### 1. Create TLS Secret

```bash
# Using Let's Encrypt (recommended for production)
# Install cert-manager first
kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.13.0/cert-manager.yaml

# Create ClusterIssuer for Let's Encrypt
kubectl apply -f - <<EOF
apiVersion: cert-manager.io/v1
kind: ClusterIssuer
metadata:
  name: letsencrypt-prod
spec:
  acme:
    server: https://acme-v02.api.letsencrypt.org/directory
    email: admin@fintech.com
    privateKeySecretRef:
      name: letsencrypt-prod-key
    solvers:
    - http01:
        ingress:
          class: nginx
EOF
```

### 2. Ingress with TLS

```yaml
# fintech-ingress-tls.yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: fintech-ingress-tls
  namespace: fintech
  annotations:
    kubernetes.io/ingress.class: "nginx"
    cert-manager.io/cluster-issuer: "letsencrypt-prod"
    
    # Force HTTPS redirect
    nginx.ingress.kubernetes.io/force-ssl-redirect: "true"
    
    # SSL protocols
    nginx.ingress.kubernetes.io/ssl-protocols: "TLSv1.2 TLSv1.3"
    
    # SSL ciphers
    nginx.ingress.kubernetes.io/ssl-ciphers: "ECDHE-ECDSA-AES128-GCM-SHA256:ECDHE-RSA-AES128-GCM-SHA256"
    
    # Security headers
    nginx.ingress.kubernetes.io/configuration-snippet: |
      more_set_headers "X-Frame-Options: DENY";
      more_set_headers "X-Content-Type-Options: nosniff";
      more_set_headers "X-XSS-Protection: 1; mode=block";
      more_set_headers "Strict-Transport-Security: max-age=31536000; includeSubDomains";
spec:
  tls:
  - hosts:
    - api.fintech.com
    secretName: fintech-tls-cert  # cert-manager creates this
  
  rules:
  - host: api.fintech.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: api-gateway  # Route to API Gateway
            port:
              number: 80
```

---

## Advanced Routing Patterns

### Path-Based Routing

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: path-based-ingress
  namespace: fintech
spec:
  rules:
  - host: api.fintech.com
    http:
      paths:
      # Admin panel (different service)
      - path: /admin
        pathType: Prefix
        backend:
          service:
            name: admin-service
            port:
              number: 80
      
      # Public API
      - path: /api
        pathType: Prefix
        backend:
          service:
            name: api-gateway
            port:
              number: 80
      
      # Documentation
      - path: /docs
        pathType: Prefix
        backend:
          service:
            name: documentation-service
            port:
              number: 80
```

### Host-Based Routing (Virtual Hosts)

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: host-based-ingress
  namespace: fintech
spec:
  rules:
  # Production API
  - host: api.fintech.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: api-gateway-prod
            port:
              number: 80
  
  # Staging API
  - host: api-staging.fintech.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: api-gateway-staging
            port:
              number: 80
  
  # Internal admin dashboard
  - host: admin.fintech.internal
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: admin-dashboard
            port:
              number: 80
```

---

## Authentication & Authorization

### Basic Auth

```yaml
# Create basic auth secret
apiVersion: v1
kind: Secret
metadata:
  name: basic-auth-secret
  namespace: fintech
type: Opaque
data:
  auth: YWRtaW46JGFwcjEkSDY1dnBkTk8kSHVXbGRyWGlFNXY2dWJCeEZjcUl6Lwo=  # admin:password

---
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: auth-ingress
  namespace: fintech
  annotations:
    nginx.ingress.kubernetes.io/auth-type: basic
    nginx.ingress.kubernetes.io/auth-secret: basic-auth-secret
    nginx.ingress.kubernetes.io/auth-realm: "Authentication Required"
spec:
  rules:
  - host: admin.fintech.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: admin-service
            port:
              number: 80
```

### OAuth2 Authentication

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: oauth2-ingress
  namespace: fintech
  annotations:
    nginx.ingress.kubernetes.io/auth-url: "https://oauth2-proxy.fintech.com/oauth2/auth"
    nginx.ingress.kubernetes.io/auth-signin: "https://oauth2-proxy.fintech.com/oauth2/start?rd=$escaped_request_uri"
spec:
  rules:
  - host: app.fintech.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: frontend-app
            port:
              number: 80
```

---

## Rate Limiting & DDoS Protection

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: rate-limited-ingress
  namespace: fintech
  annotations:
    # Rate limiting
    nginx.ingress.kubernetes.io/limit-rps: "10"  # 10 requests/second per IP
    nginx.ingress.kubernetes.io/limit-burst-multiplier: "5"  # Allow bursts up to 50 req/s
    nginx.ingress.kubernetes.io/limit-rate-after: "1024"  # Rate limit after 1KB
    
    # Connection limits
    nginx.ingress.kubernetes.io/limit-connections: "20"
    
    # Whitelist specific IPs (bypass rate limiting)
    nginx.ingress.kubernetes.io/limit-whitelist: "10.0.0.0/8,192.168.0.0/16"
spec:
  rules:
  - host: api.fintech.com
    http:
      paths:
      - path: /api/v1/public
        pathType: Prefix
        backend:
          service:
            name: public-api-service
            port:
              number: 80
```

---

## Custom Error Pages

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: custom-error-pages
  namespace: fintech
data:
  404.html: |
    <!DOCTYPE html>
    <html>
    <head><title>404 Not Found</title></head>
    <body>
      <h1>Page Not Found</h1>
      <p>The requested resource was not found.</p>
    </body>
    </html>
  
  503.html: |
    <!DOCTYPE html>
    <html>
    <head><title>503 Service Unavailable</title></head>
    <body>
      <h1>Service Temporarily Unavailable</h1>
      <p>We're performing maintenance. Please try again later.</p>
    </body>
    </html>

---
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: custom-errors-ingress
  namespace: fintech
  annotations:
    nginx.ingress.kubernetes.io/custom-http-errors: "404,503"
    nginx.ingress.kubernetes.io/default-backend: error-pages-service
spec:
  rules:
  - host: api.fintech.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: api-gateway
            port:
              number: 80
```

---

## Canary Deployments

```yaml
# Production Ingress (90% traffic)
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: transaction-service-prod
  namespace: fintech
spec:
  rules:
  - host: api.fintech.com
    http:
      paths:
      - path: /api/v1/transactions
        pathType: Prefix
        backend:
          service:
            name: transaction-service-v1
            port:
              number: 80

---
# Canary Ingress (10% traffic)
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: transaction-service-canary
  namespace: fintech
  annotations:
    nginx.ingress.kubernetes.io/canary: "true"
    nginx.ingress.kubernetes.io/canary-weight: "10"  # 10% traffic
spec:
  rules:
  - host: api.fintech.com
    http:
      paths:
      - path: /api/v1/transactions
        pathType: Prefix
        backend:
          service:
            name: transaction-service-v2  # New version
            port:
              number: 80
```

---

## Monitoring & Observability

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: monitored-ingress
  namespace: fintech
  annotations:
    # Enable Prometheus metrics
    nginx.ingress.kubernetes.io/enable-metrics: "true"
    
    # Enable request tracing
    nginx.ingress.kubernetes.io/enable-opentracing: "true"
    
    # Add custom headers for monitoring
    nginx.ingress.kubernetes.io/configuration-snippet: |
      more_set_headers "X-Request-ID: $request_id";
      more_set_headers "X-Real-IP: $remote_addr";
spec:
  rules:
  - host: api.fintech.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: api-gateway
            port:
              number: 80
```

---

## Deploy Ingress

```bash
# Apply all Ingress configurations
kubectl apply -f fintech-ingress-tls.yaml

# Check Ingress status
kubectl get ingress -n fintech

# Describe Ingress (view events, IPs)
kubectl describe ingress fintech-ingress-tls -n fintech

# View Ingress controller logs
kubectl logs -n ingress-nginx -l app.kubernetes.io/name=ingress-nginx

# Test Ingress
curl -k https://api.fintech.com/api/v1/transactions
```

---

## Production Best Practices

1. **Always use TLS** - Never expose APIs over HTTP
2. **Rate limiting** - Protect against DDoS and abuse
3. **Authentication** - Secure sensitive endpoints
4. **CORS properly configured** - Allow only trusted origins
5. **Monitor metrics** - Track request rates, errors, latency
6. **Custom error pages** - Better user experience
7. **Canary deployments** - Gradual rollout of new versions
8. **Resource limits** - Prevent ingress controller overload
9. **High availability** - Run multiple ingress controller replicas
10. **Regular updates** - Keep ingress controller up to date

---

## Summary

**Ingress provides:**
- ✅ Single entry point for external traffic
- ✅ Path and host-based routing
- ✅ TLS termination
- ✅ Load balancing
- ✅ Rate limiting
- ✅ Authentication
- ✅ Custom error pages
- ✅ Canary deployments

**Cost savings:** One LoadBalancer instead of one per service!

---

**Next:** [Complete Service Implementation Guide](./COMPLETE_SERVICE_IMPLEMENTATION.md)
