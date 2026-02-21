# ===== STAGE 1: Build =====
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw -q -e -B dependency:go-offline

COPY src src
RUN ./mvnw -q -e -B package -DskipTests

# ===== STAGE 2: Runtime =====
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "app.jar"]