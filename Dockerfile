FROM eclipse-temurin:21-jdk-jammy AS build

USER root

WORKDIR /app

COPY . .

RUN chmod +x gradlew && apt-get update && apt-get install -y dos2unix && dos2unix gradlew

RUN ./gradlew build --no-daemon -x test

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]