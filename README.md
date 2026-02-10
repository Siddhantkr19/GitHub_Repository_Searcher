

```markdown
# GitHub Repository Searcher 🔍

A robust Backend REST API built with **Spring Boot** that searches for GitHub repositories, stores them in a **PostgreSQL** database, and provides advanced filtering capabilities.

> **Developed by:** Siddhant Kumar
> **Assignment:** FreightFox Backend Role

---

## 🚀 Features

* **GitHub Integration**: Fetches repository data (Stars, Forks, Owner, Language, etc.) directly from the GitHub REST API.
* **Smart Storage (Upsert)**: Implements "Update-or-Insert" logic. If a repository exists, it updates the statistics; otherwise, it creates a new record to prevent duplicates.
* **Advanced Filtering**: Retrieve stored repositories filtered by **Language**, **Minimum Stars**, and sorted by **Stars**, **Forks**, or **Last Updated**.
* **Robust Error Handling**: Gracefully handles API failures and edge cases.
* **Test-Driven Development (TDD)**: Includes JUnit tests using Mockito to verify service logic without external dependencies.

---

## 🛠️ Tech Stack

* **Language**: Java 17
* **Framework**: Spring Boot 3.4.2
* **Database**: PostgreSQL
* **ORM**: Spring Data JPA (Hibernate)
* **Tools**: Maven, Lombok, Postman
* **Testing**: JUnit 5, Mockito

---

## ⚙️ Setup & Installation

### 1. Prerequisites
* Java 17 or higher
* PostgreSQL installed and running
* Maven

### 2. Clone the Repository
```bash
git clone https://github.com/Siddhantkr19/GitHub_Repository_Searcher
cd github-repo-searcher

```

### 3. Database Configuration

1. Open **pgAdmin** or your terminal.
2. Create a database named `github_repo_searcher`.
3. Open `src/main/resources/application.properties` and update your credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/github_repo_searcher
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD

```

### 4. Build and Run

```bash
mvn clean install
mvn spring-boot:run

```

The application will start on `http://localhost:8080`.

---

## 📡 API Documentation

### 1. Search & Save Repositories

Fetches data from GitHub and saves/updates it in the local database.

* **Endpoint**: `POST /api/github/search`
* **Content-Type**: `application/json`

**Request Body:**

```json
{
  "query": "spring boot",
  "language": "Java",
  "sort": "stars"
}

```

**Response:**

```json
{
    "message": "Repositories fetched and saved successfully",
    "searchCriteria": {
        "query": "spring boot",
        "language": "Java",
        "sort": "stars"
    }
}

```

### 2. Retrieve Stored Repositories

Fetches repositories from the local database with optional filters.

* **Endpoint**: `GET /api/github/repositories`
* **Parameters**:
* `language` (optional): e.g., `Java`
* `minStars` (optional): e.g., `500`
* `sort` (optional): `stars`, `forks`, or `updated` (Default: `stars`)



**Example Request:**
`GET /api/github/repositories?language=Java&minStars=1000&sort=forks`

**Response:**

```json
{
    "count": 5,
    "repositories": [
        {
            "id": 12345,
            "name": "spring-boot",
            "description": "Spring Boot",
            "ownerName": "spring-projects",
            "language": "Java",
            "starsCount": 75000,
            "forksCount": 40000,
            "lastUpdated": "2025-02-10T12:00:00"
        }
    ]
}

```

---

## 🧪 Running Tests

This project uses **JUnit 5** and **Mockito** for unit testing the service layer.

Run tests using Maven:

```bash
mvn test

```

---

## 📂 Project Structure

```
src/main/java/com/github/repository/searcher
├── controller      # REST Controllers
├── dto             # Data Transfer Objects
├── entity          # JPA Entities
├── repository      # Database Interfaces
├── service         # Business Logic
└── config          # App Configuration

```


```
