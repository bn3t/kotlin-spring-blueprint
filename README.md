[![Build Status](https://github.com/bn3t/kotlin-spring-blueprint/actions/workflows/maven.yml/badge.svg)](https://github.com/bn3t/kotlin-spring-blueprint/actions/workflows/maven.yml)

# Spring Boot Blueprint Application (Kotlin, Spring JPA, Hibernate)

This project serves as a blueprint for creating Spring Boot applications using Kotlin, Spring Data JPA, and Hibernate.
It demonstrates a simple CRUD REST API based on an OpenAPI specification and showcases various types of tests, including
unit tests, integration tests, and end-to-end (E2E) tests.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [OpenAPI Specification](#openapi-specification)
- [Setup and Run](#setup-and-run)
- [Testing](#testing)
    - [Unit Tests](#unit-tests)
    - [Integration Tests](#integration-tests)
    - [End-to-End (E2E) Tests](#end-to-end-e2e-tests)
- [Endpoints](#endpoints)
- [Contributing](#contributing)
- [License](#license)

## Overview

This application demonstrates how to create a Spring Boot-based REST API using Kotlin. It provides a basic CRUD (Create,
Read, Update, Delete) interface for managing resources (e.g., books) with a well-defined structure using Spring Data
JPA, Hibernate, and an OpenAPI specification for API documentation.

The project includes:

- A simple REST interface for books.
- CRUD operations with database persistence.
- Unit tests for business logic.
- Integration tests to verify data interaction.
- End-to-End (E2E) tests for overall functionality.

## Features

- **Spring Boot**: Lightweight, rapid application development framework.
- **Kotlin**: Modern programming language for JVM with expressive syntax and safety features.
- **Spring Data JPA & Hibernate**: Database interaction and ORM mapping.
- **OpenAPI Specification**: Automatically generated documentation for the REST API.
- **CRUD Interface**: Full CRUD operations exposed via REST.
- **Testing**: Unit, integration, and end-to-end tests.

## Technologies

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Hibernate](https://hibernate.org/)
- [Kotlin](https://kotlinlang.org/)
- [OpenAPI](https://swagger.io/specification/)
- [JUnit 5](https://junit.org/junit5/)
- [MockK](https://mockk.io/)
- [Testcontainers](https://www.testcontainers.org/)
- [MockMVC](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html)

## Project Structure

```
├── src
│   ├── main
│   │   ├── kotlin
│   │   │   ├── controller
│   │   │   ├── dto
│   │   │   ├── exception
│   │   │   ├── mapper
│   │   │   ├── model
│   │   │   ├── repository
│   │   │   ├── service
│   │   │   └── KotlinBootApplication.kt
│   │   └── resources
│   │       ├── application.yml
│   └── test
│       ├── kotlin
│       └── resources
└── README.md
```

### Key Folders:

- **controller**: Defines the REST API endpoints.
- **dto**: Data Transfer Objects used for API communication.
- **model**: JPA entities that map to database tables.
- **repository**: Interfaces for database access using Spring Data JPA.
- **service**: Business logic layer, handling CRUD operations.
- **mapper**: MapStruct mappers to do type conversion between layers.
- **test**: Contains unit, integration, and E2E test cases.

## OpenAPI Specification

The application uses an OpenAPI specification for defining the API interface. The API specification can be found in the
`book-api.yaml` file.

The API endpoints are based on this specification and adhere to standard practices for RESTful services.

## Setup and Run

### Prerequisites

- Java 21 or higher.
- Maven.
- Docker (for testing with Testcontainers).

### Steps to Run

1. **Clone the repository**:
    ```bash
    git clone https://github.com/bn3t/kotlin-spring-blueprint.git
    cd spring-boot-blueprint-kotlin
    ```

2. **Build the application**:
    ```bash
    ./mvnw clean install
    ```

3. **Run the application**:
    ```bash
    ./mvnw spring-boot:run
    ```

4. **Access the API**:
    - **API**: [http://localhost:8080/api/books](http://localhost:8080/api/books)

## Endpoints

### Book API Endpoints

| Method | Endpoint                 | Description             |
|--------|--------------------------|-------------------------|
| GET    | `/api/books`             | Get all books           |
| GET    | `/api/books/{bookId}`    | Get a book by ID        |
| GET    | `/api/books/isbn/{isbn}` | Get a book by ISBN      |
| POST   | `/api/books`             | Add a new book          |
| PUT    | `/api/books/{bookId}`    | Update an existing book |
| DELETE | `/api/books/{bookId}`    | Delete a book by ID     |

## Contributing

Contributions are welcome! Feel free to fork the repository and submit pull requests.

### Steps to Contribute

1. Fork the repository.
2. Create a feature branch: `git checkout -b my-feature-branch`
3. Commit your changes: `git commit -m "Added a new feature"`
4. Push to the branch: `git push origin my-feature-branch`
5. Open a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

This blueprint demonstrates how to build and test a modern Spring Boot application using Kotlin and best practices for
REST API development. Feel free to adapt it to your needs and use it as a starting point for more complex projects!
