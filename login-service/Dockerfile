# --- Build Stage ---
FROM  maven:3.9.16-eclipse-temurin-25 AS build

WORKDIR /app

COPY pom.xml .

# Copy only pom.xml first to leverage Docker layer caching for dependencies
RUN mvn dependency:go-offline -B

COPY src ./src


RUN mvn clean package -DskipTests

# --- Running Stage ---

FROM eclipse-temurin:25-jre-alpine


WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/login-service-*.jar app.jar

# Expose the port your service runs on
EXPOSE 8081

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/login-service
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgres

ENTRYPOINT ["java","-jar", "app.jar"]