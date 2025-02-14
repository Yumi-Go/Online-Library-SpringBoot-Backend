# Online Library with AI Insights

An **end-to-end** full-stack application featuring:
- **Part 1**: A Spring Boot backend (REST APIs, H2 database, AI integration with OpenAI)
- **Part 2**: A Next.js 13 frontend with Tailwind CSS for UI styling

## Table of Contents

1. [Overview](#overview)
2. [Backend (Spring Boot)](#backend-spring-boot)
   - [Requirements](#requirements)
   - [Installation & Setup](#installation--setup)
   - [Project Structure](#project-structure)
   - [Running & Testing](#running--testing)
   - [Endpoints](#endpoints)

---

## Overview

**Online Library** is a sample app for managing books (CRUD) with optional **AI-generated** insights or taglines. The backend is **Spring Boot** (with an in-memory H2 database), while the frontend is **Next.js** (with **Tailwind CSS** for styling).

**Features**:
- **Create** new books with title, author, ISBN, year, description.
- **View** all books or a single book’s details.
- **Search** books by title and/or author.
- **AI Insights** endpoint using **OpenAI** (or a mock).
- **Tailwind**-enhanced **UI** in Next.js.

---

## Backend (Spring Boot)

### Requirements

1. **Java 17+** (This project uses Java 21, but version 17 is the minimum).
2. **Maven**
3. Optional: environment variable `OPENAI_API_KEY` if calling a real AI service.

### Installation & Setup

1. Clone or download the **https://github.com/Yumi-Go/Online-Library-SpringBoot-Backend.git** repository.
2. Navigate to the **project root** where `pom.xml` is.
3. Run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
4. The Spring Boot app will start at `http://localhost:8080`.

### Project Structure

```
online-library-spring-boot-backend
 ┣ src
 ┃ ┣ main
 ┃ ┃ ┣ java
 ┃ ┃ ┃ └ com.example.online_library_spring_boot_backend
 ┃ ┃ ┃   ├ OnlineLibrarySpringBootBackendApplication.java
 ┃ ┃ ┃   ├ WebConfig.java         (CORS config)
 ┃ ┃ ┃   ├ controller
 ┃ ┃ ┃   ├ dto
 ┃ ┃ ┃   ├ exception
 ┃ ┃ ┃   ├ model
 ┃ ┃ ┃   ├ repository
 ┃ ┃ ┃   └ service
 ┃ ┃ ┗ resources
 ┃ ┃    └ application.properties
 ┗ pom.xml
```

Notable files:
- `Book.java` (entity)
- `BookRepository.java` (CRUD with JPA)
- `BookController.java` (REST endpoints)
- `AiService.java` (OpenAI integration)
- `GlobalExceptionHandlers.java` (validation + runtime error handling)
- `WebConfig.java` (CORS enabled for `localhost:3000`)

### Running & Testing

- **Run** the app:
  ```bash
  mvn spring-boot:run
  ```
  The server listens on `http://localhost:8080`.

- **Access H2 console** (optional):
  ```
  http://localhost:8080/h2-console
  ```
  (User: `sa`, no password, DB: `jdbc:h2:mem:testdb`)

- **Integration tests** (MockMvc + WireMock):
  ```bash
  mvn test
  ```
  The tests appear under `src/test/java/com/example/online_library_spring_boot_backend/OnlineLibrarySpringBootBackendApplicationTests.java`.

### Endpoints

| Endpoint                       | Method | Description                             |
|--------------------------------|--------|-----------------------------------------|
| `/books`                       | POST   | Create a new book                       |
| `/books`                       | GET    | Get all books                           |
| `/books/{id}`                  | GET    | Get a single book by ID (or 404)        |
| `/books/{id}`                  | PUT    | Update an existing book (or 404)        |
| `/books/{id}`                  | DELETE | Delete a book (or 404)                  |
| `/books/search?title=&author=` | GET    | Search by partial title/author          |
| `/books/{id}/ai-insights`      | GET    | AI tagline for a given book (mock/real) |

**OpenAI Integration**:
- Configured via `openai.api.key` and `openai.api.url` in `application.properties`.
- If you want to use **real** GPT engine, set the correct model name and an actual API key.
- If you prefer a **mock** or offline mode, you can comment out or replace the `webClient.post()` code in `AiService`.



### Viewing the OpenAPI Docs via /v3/api-docs
When Springdoc is installed and configured correctly, an OpenAPI specification is automatically generated at:
 **http://localhost:8080/v3/api-docs**
