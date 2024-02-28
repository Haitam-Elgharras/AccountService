FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8082
ADD target/AccountService-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]