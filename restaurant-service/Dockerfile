# Use the official Maven image to build the application
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .

# Build all the dependencies in preparation to go offline.
# This is a separate step so the dependencies will be cached unless changes to pom.xml are made.
RUN mvn dependency:go-offline -B

COPY application.properties src/main/resources/application.properties

# Copy the project source code
COPY src src

# Package the application
RUN mvn package -DskipTests

# Use the official OpenJDK 17 base image for the runtime
FROM amazoncorretto:17-alpine3.19

# Set the working directory in the container
WORKDIR /app

# Define a build argument for the application version
ARG APP_VERSION=latest

# Copy the packaged Spring Boot application JAR file from the build stage
COPY --from=build /app/target/restaurant-service-${APP_VERSION}.jar /app/restaurant-service.jar

# Expose the port that the application runs on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "restaurant-service.jar"]