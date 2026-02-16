# Multi-stage Dockerfile for Spring Boot 3.2 + Java 21
# Optimized for production use in FinTech environments

# ===================================
# Stage 1: Build Stage
# ===================================
FROM maven:3.9-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy Maven files first (better caching)
# Changes to pom.xml are less frequent than source code
COPY pom.xml .

# Download dependencies (this layer will be cached)
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
# -DskipTests: Skip tests during build (run them in CI/CD)
# -B: Batch mode (less verbose output)
RUN mvn clean package -DskipTests -B

# ===================================
# Stage 2: Runtime Stage
# ===================================
FROM eclipse-temurin:21-jre-alpine

# Install additional tools for debugging (optional in production)
RUN apk add --no-cache curl wget

# Create app directory
WORKDIR /app

# Create non-root user for security
# Running as root is a security risk
RUN addgroup -S spring && adduser -S spring -G spring

# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Change ownership to spring user
RUN chown spring:spring app.jar

# Switch to non-root user
USER spring:spring

# Expose application port
EXPOSE 8080

# Health check
# Kubernetes can also use this to determine container health
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# JVM optimizations for containers
ENV JAVA_OPTS="\
  -XX:+UseZGC \
  -XX:+UseContainerSupport \
  -XX:MaxRAMPercentage=75.0 \
  -XX:InitialRAMPercentage=50.0 \
  -XX:+OptimizeStringConcat \
  -Djava.security.egd=file:/dev/./urandom"

# Run the application
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
