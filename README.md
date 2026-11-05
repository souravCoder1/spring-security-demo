# Spring Security Demo

A minimal Spring Boot application demonstrating role-based authorization and secure REST endpoints using Spring Security.

## Features

- Role-based security with `STUDENT` and `ADMIN` access rules
- Secure form login and HTTP Basic authentication
- BCrypt password hashing
- Sample REST API for student records
- Java 8 compatible Spring Boot project

## Tech stack

- Java 8
- Spring Boot 2.7.5
- Spring Security
- Maven

## Prerequisites

- JDK 8 or later
- Maven 3.8+

## Running the application

1. Clone the repository.
2. Open a terminal in the project root.
3. Run:

```bash
./mvnw spring-boot:run
```

The app starts on:

```text
http://localhost:8080
```

## Default users

The demo config includes two in-memory users for testing:

- Username: `sourav` | Password: `123` | Role: `STUDENT`
- Username: `anna` | Password: `123` | Role: `ADMIN`

## API examples

### List students

```bash
curl -u sourav:123 http://localhost:8080/api/v1/students
```

### Get a specific student

```bash
curl -u anna:123 http://localhost:8080/api/v1/students/1
```

## Project structure

```text
src/
├── main/
│   ├── java/com/sourav/springsecurity/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── model/
│   │   └── SpringSecurityApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/sourav/springsecurity/
```

## Notes

This project is intentionally small and educational. It focuses on clear Spring Security patterns and is a good starting point for expanding to a full application with database-backed users, JWT authentication, or authorization rules for additional domain entities.
