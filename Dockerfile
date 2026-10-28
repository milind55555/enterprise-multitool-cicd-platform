# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY app/pom.xml ./pom.xml
COPY app/src ./src
RUN mvn -B -ntp clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
RUN apt-get update && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/* \
    && useradd --system --uid 10001 --create-home appuser
COPY --from=build /workspace/target/enterprise-demo.jar /app/app.jar
USER 10001
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD ["curl", "-fsS", "http://127.0.0.1:8080/actuator/health"]
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]
