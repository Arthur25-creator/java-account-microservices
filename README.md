# java-account-microservices
Java Microservice Account Creation

# Java Microservices Coding Exam

## Microservices Implemented

- **Account Service** (port `8080`)
  - `/api/v1/account` — POST (Create account)

## Tech Stack

- Java 8
- Spring Boot 2.6
- H2 In-Memory DB
- JPA
- JUnit + Mockito for unit testing
- Postman for manual testing

## How to Run

```bash
cd account-service
mvn spring-boot:run

cd ../customer-service
mvn spring-boot:run

