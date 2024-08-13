FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/trip-service-0.0.1-SNAPSHOT.jar /app/trip-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "trip-service.jar"]
