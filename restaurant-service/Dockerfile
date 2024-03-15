FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY application.properties.docker src/main/resources/application.properties.docker
COPY src src

RUN rm src/main/resources/application.properties
RUN mv src/main/resources/application.properties.docker src/main/resources/application.properties

RUN mvn package -DskipTests

FROM amazoncorretto:17-alpine3.19

WORKDIR /app

ARG APP_VERSION=latest

COPY --from=build /app/target/restaurant-service-${APP_VERSION}.jar /app/restaurant-service.jar

EXPOSE 8080

CMD ["java", "-jar", "restaurant-service.jar"]