# Build image
FROM maven:3.9.6-amazoncorretto-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY application.properties.docker src/main/resources/application.properties.docker
COPY src src

RUN rm src/main/resources/application.properties
RUN mv src/main/resources/application.properties.docker src/main/resources/application.properties

RUN mvn package -DskipTests

# Runtime image
FROM amazoncorretto:21-alpine3.19

WORKDIR /app

ARG APP_VERSION=latest

COPY --from=build /app/target/user-service-${APP_VERSION}.jar /app/user-service.jar

EXPOSE 8080

CMD ["java", "-jar", "user-service.jar"]