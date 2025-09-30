# Use a lightweight OpenJDK 17 base image
FROM eclipse-temurin:17-jre-alpine

# Set a working directory inside the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/account-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app listens on (default 8080)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]
