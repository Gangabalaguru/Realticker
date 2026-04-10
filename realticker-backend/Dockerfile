# Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace

# Copy Maven build files and download dependencies first for layer caching
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Copy source, build the application, and skip tests for faster image builds
COPY src src
RUN chmod +x mvnw && ./mvnw -B -DskipTests package

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the built Spring Boot fat jar
COPY --from=build /workspace/target/realticker-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENV JAVA_OPTS=""

# Render passes the port via PORT environment variable
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
