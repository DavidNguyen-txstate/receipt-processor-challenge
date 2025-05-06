# ---- Build Stage ----
FROM eclipse-temurin:24-jdk-alpine AS build

WORKDIR /app

# Copy Maven wrapper and pom.xml first for dependency caching
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

# Download dependencies (will be cached if pom.xml hasn't changed)
RUN ./mvnw dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the application JAR
RUN ./mvnw clean package -DskipTests

# ---- Run Stage ----
FROM eclipse-temurin:24-jdk-alpine

WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/receipt-processor-0.0.1-SNAPSHOT.jar receipt-processor.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "receipt-processor.jar"]