#FROM eclipse-temurin:17-jdk-alpine
#EXPOSE 8082
#ADD target/AccountService-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]


# Use an official base image (e.g., openjdk)
FROM openjdk:17-jdk-slim

# Expose the port your Spring application will listen on
EXPOSE 8082

# Set the working directory within the container
WORKDIR /app

# Copy the Spring Boot JAR file into the container
COPY target/AccountService-0.0.1-SNAPSHOT.jar app.jar

# Define the command to start your Spring application
ENTRYPOINT ["java", "-jar", "app.jar"]
