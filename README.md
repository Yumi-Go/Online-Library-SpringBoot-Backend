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
3. [Frontend (Next.js + Tailwind)](#frontend-nextjs--tailwind)
   - [Requirements](#requirements-1)
   - [Installation & Setup](#installation--setup-1)
   - [Project Structure](#project-structure-1)
   - [Running & Testing](#running--testing-1)
   - [Using the App](#using-the-app)
4. [License](#license)
5. [Additional Notes](#additional-notes)

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

---

## Frontend (Next.js + Tailwind)

### Requirements

1. **Node.js** 16+  
2. **npm** or **yarn**

### Installation & Setup

1. Clone or download the **https://github.com/Yumi-Go/Online-Library-NextJs-Frontend.git** folder.
2. Navigate to the **project root** where `package.json` is.
3. Install dependencies:
   ```bash
   npm install
   ```
4. Start development server:
   ```bash
   npm run dev
   ```
   It runs at `http://localhost:3000`.

> Make sure your Spring Boot backend is running on **port 8080**. If you changed the backend port, update the fetch URLs in React code.

### Project Structure

```
online-library-nextjs-frontend
 ┣ app
 ┃ ┣ book
 ┃ ┃ ┣ [id]
 ┃ ┃ ┃ └ page.js   (single book details + AI tagline)
 ┃ ┃ ┗ new
 ┃ ┃   └ page.js   (create new book form)
 ┃ ┣ page.js       (home page, search + list)
 ┃ ┣ layout.js     (root layout, imports Tailwind)
 ┃ ┗ globals.css   (Tailwind CSS)
 ┣ next.config.mjs
 ┣ package.json
 ┣ postcss.config.js
 ┣ tailwind.config.js
 ┗ ...
```

**Key files**:
- **`layout.js`**: root layout, imports `globals.css`.
- **`globals.css`**: includes `@tailwind base; @tailwind components; @tailwind utilities;`.
- **`page.js`**: the main home page for listing/searching books.
- **`book/[id]/page.js`**: route for book details + AI button.
- **`book/new/page.js`**: route for creating a new book.

### Running & Testing

- **Local dev**:
  ```bash
  npm run dev
  ```
  Visit [http://localhost:3000](http://localhost:3000).

- **Check** that it successfully fetches from the backend at `http://localhost:8080`.

> If you see **CORS** errors, ensure your Spring Boot config (`WebConfig.java`) or other approach allows requests from `http://localhost:3000`.

### Using the App

- **Home Page** (`/`):
  - Shows a **search form** (by title/author).
  - Lists all matching books.
  - Links to **create a new book** or see **book details**.
- **Create** new books:
  - At `/book/new`, fill out the form and submit.
- **View & AI Insights**:
  - On a book’s page (e.g., `/book/{id}`), see the book’s data.
  - Click “Get AI Insights” to fetch an AI-generated tagline from the backend.  
    (Works if your `AiService` is configured or mocked.)
