# Stock Manager Backend

A robust RESTful API backend application for managing stock/inventory operations built with Spring Boot.

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-green.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6+-orange.svg)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 🎯 Overview

Stock Manager Backend is a Spring Boot-based REST API that provides comprehensive inventory management capabilities. It handles product management, categorization, user authentication, and stock tracking with features like bulk operations and low stock alerts.

## ✨ Features

- **Product Management**
  - Create, read, update, and delete products
  - Bulk product creation
  - Low stock alerts
  - Pagination support for product listings
  - Product categorization

- **Category Management**
  - CRUD operations for product categories
  - Hierarchical category support

- **User Management & Authentication**
  - JWT-based authentication
  - Secure user authentication and authorization
  - Spring Security integration

- **Additional Features**
  - RESTful API design
  - Database migrations with Flyway
  - Internationalization (i18n) support
  - Exception handling with custom error messages
  - MapStruct for DTO mapping
  - Actuator endpoints for monitoring

## 🏗️ Architecture & Design

### Component Decomposition

The application follows a **layered architecture** pattern with clear separation of concerns:

```
┌─────────────────────────────────────────────┐
│         Presentation Layer (API)            │
│  - REST Controllers                         │
│  - Request/Response DTOs                    │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│         Service Layer (Business Logic)      │
│  - Service Interfaces                       │
│  - Service Implementations                  │
│  - Business Rules & Validation              │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│         Persistence Layer (Data Access)     │
│  - JPA Repositories                         │
│  - Entity Models                            │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│            Database (PostgreSQL)            │
└─────────────────────────────────────────────┘
```

**Key Components:**

1. **API Layer** (`api/`)
   - `AuthController`, `ProductController`, `CategoryController`, `UserController`
   - Handle HTTP requests/responses
   - Input validation and request mapping

2. **Service Layer** (`service/`)
   - Business logic implementation
   - Transaction management
   - Orchestrates operations between multiple repositories

3. **Repository Layer** (`repo/`)
   - `ProductRepo`, `CategoryRepo`, `UserRepo`
   - Data access abstraction using Spring Data JPA
   - Custom query methods

4. **Entity Layer** (`entity/`)
   - JPA entities representing database tables
   - `Product`, `Category`, `User`, `StockLog`

5. **DTO Layer** (`dto/`)
   - Data Transfer Objects for API contracts
   - Separate request and response DTOs
   - Prevents exposing internal entity structure

6. **Mapper Layer** (`mapper/`)
   - MapStruct mappers for entity-DTO conversions
   - `ProductMapper`, `CategoryMapper`, `UserMapper`
   - Automatic type-safe mapping

7. **Configuration Layer** (`config/`)
   - Security configuration (`SecurityConfig`)
   - Web configuration (`WebConfig`)
   - CORS and authentication setup

8. **Filter Layer** (`filter/`)
   - JWT authentication filters
   - Request/response interceptors

### Global Exception Handling

The application implements a **centralized exception handling** mechanism using `@ControllerAdvice`:

**`GlobalExceptionHandler`** class provides:

- **Unified error responses** across all endpoints
- **Custom exception handling** for domain-specific errors:
  - `ProductNotFoundException` → 404 NOT FOUND
  - `CategoryNotFoundException` → 404 NOT FOUND
  - `DuplicateCategoryException` → 409 CONFLICT
  - `DuplicateEntityException` → 409 CONFLICT
  - `ValidationException` → 400 BAD REQUEST

- **Internationalized error messages** using `MessageUtil`
- **Consistent error response format**:
  ```json
  {
    "status": "UNSUCCESSFUL",
    "message": "Localized error message"
  }
  ```

- **Logging integration** for all exceptions
- **Clean controller code** - no try-catch blocks needed in controllers

**Benefits:**
- ✅ Separation of error handling from business logic
- ✅ Consistent API error responses
- ✅ Better maintainability and testability
- ✅ Automatic HTTP status code mapping
- ✅ Support for multiple languages via i18n

## 🛠 Technology Stack

- **Framework:** Spring Boot 4.0.0
- **Language:** Java 17
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA / Hibernate
- **Security:** Spring Security + JWT
<!-- - **Database Migration:** Flyway -->
- **Object Mapping:** MapStruct 1.6.3
- **Build Tool:** Maven
- **Additional Libraries:**
  - Lombok
  - Jackson
  - Spring Boot Actuator
  - Spring Boot DevTools

## 📦 Prerequisites

Before running this application, ensure you have the following installed:

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+ 
- Git

## 🚀 Installation

1. **Clone the repository**
   \`\`\`bash
   git clone https://github.com/nirasha-nelki/stockmanager_be.git
   cd stockmanager_be
   \`\`\`

2. **Build the project**
   \`\`\`bash
   ./mvnw clean install
   \`\`\`
   
   On Windows:
   \`\`\`bash
   mvnw.cmd clean install
   \`\`\`

## ⚙️ Configuration

### Creating the Application Properties File

> ⚠️ **Note:** The `application.properties` file is not included in the repository for security reasons (contains sensitive database credentials). You need to create it manually.

1. **Use the example template (Recommended)**

   Copy the example file and rename it:

   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```

   Then edit `application.properties` and update with your actual database credentials.

2. **Or create from scratch**

   Navigate to `src/main/resources/` and create a new file named `application.properties`:

   ```bash
   touch src/main/resources/application.properties
   ```

3. **Add the following configuration**

   Copy and paste the following content into your `application.properties` file:

   ```properties
   # Application Name
   spring.application.name=stockmanager_be

   # Server Configuration
   server.port=8080

   # Database Configuration (PostgreSQL)
   spring.datasource.url=jdbc:postgresql://localhost:5432/stockmanager?createDatabaseIfNotExist=true
   spring.datasource.username=your_database_username
   spring.datasource.password=your_database_password

   # JPA/Hibernate Configuration
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.generate-ddl=true
   spring.jpa.show-sql=true

   # Hibernate Performance Optimization
   spring.jpa.properties.hibernate.jdbc.batch_size=50
   spring.jpa.properties.hibernate.order_inserts=true
   spring.jpa.properties.hibernate.order_updates=true
   spring.jpa.properties.hibernate.jdbc.batch_versioned_data=true

   # Optional: Uncomment for detailed SQL logging
   # spring.jpa.properties.hibernate.format_sql=true
   # spring.jpa.properties.hibernate.use_sql_comments=true
   ```

3. **Configure your database credentials**

   Replace the following placeholders with your actual database information:
   - `your_database_username` - Your PostgreSQL/MySQL username
   - `your_database_password` - Your PostgreSQL/MySQL password
   - `stockmanager` - Database name (create this database before running the app)


### Configuration Properties Explained

| Property | Description | Default |
|----------|-------------|---------|
| `server.port` | Port on which the application runs | 8080 |
| `spring.jpa.hibernate.ddl-auto` | Schema generation strategy (update/validate/create/create-drop) | update |
| `spring.jpa.show-sql` | Show SQL queries in console | true |
| `spring.jpa.properties.hibernate.jdbc.batch_size` | Number of statements to batch | 50 |

### Security Configuration

JWT and other security settings can be configured in the `SecurityConfig.java` class located at:
```
src/main/java/com/stockmanager/stockmanager_be/config/SecurityConfig.java
```

## 🏃 Running the Application

### Using Maven

\`\`\`bash
./mvnw spring-boot:run
\`\`\`

### Using Java

\`\`\`bash
./mvnw clean package
java -jar target/stockmanager_be-0.0.1-SNAPSHOT.jar
\`\`\`

The application will start on \`http://localhost:8080\`

## 📡 API Endpoints

### Authentication
- \`POST /api/v1/auth/login\` - User login
- \`POST /api/v1/auth/register\` - User registration

### Products
- \`POST /api/v1/product/add\` - Create a new product
- \`POST /api/v1/product/list/add\` - Bulk product creation
- \`GET /api/v1/product/{productId}\` - Get product by ID
- \`GET /api/v1/product/all\` - Get all products
- \`GET /api/v1/product/low-stock\` - Get low stock products
- \`PUT /api/v1/product/update\` - Update product
- \`DELETE /api/v1/product/{productId}\` - Delete product

### Categories
- \`POST /api/v1/category/add\` - Create a new category
- \`GET /api/v1/category/{id}\` - Get category by ID
- \`GET /api/v1/category/all\` - Get all categories
- \`DELETE /api/v1/category/{id}\` - Delete category

### Users
- \`GET /api/v1/user/all\` - Get all users
- \`GET /api/v1/user/{id}\` - Get user by ID

> **Note:** Most endpoints require JWT authentication. Include the JWT token in the Authorization header: \`Authorization: Bearer <token>\`

## 🗄 Database

The application uses **JPA/Hibernate** for automatic schema management with the `spring.jpa.hibernate.ddl-auto=update` setting, which creates and updates tables based on your entity classes.

### Database Setup

1. **Create the database:**

   ```bash
   # PostgreSQL
   psql -U postgres
   CREATE DATABASE stockmanager;
   \q
   ```

2. **Run the application:**

   The tables will be automatically created based on your JPA entities (`Product`, `Category`, `User`, `StockLog`)

### Flyway Migrations (Optional)

For production environments or when you need controlled schema changes, you can enable **Flyway** for database migrations.

**To enable Flyway:**

1. Update your `application.properties`:

   ```properties
   # Disable Hibernate auto DDL (important!)
   spring.jpa.hibernate.ddl-auto=validate
   
   # Enable Flyway
   spring.flyway.enabled=true
   spring.flyway.baseline-on-migrate=true
   spring.flyway.locations=classpath:db/migration
   ```

2. Create migration scripts in `src/main/resources/db/migration/` with the naming pattern:
   - `V1__initial_schema.sql`
   - `V2__add_stock_log.sql`
   - `V3__alter_product_table.sql`

> ⚠️ **Important:** When using Flyway, set `spring.jpa.hibernate.ddl-auto=validate` to prevent Hibernate from managing the schema automatically. Flyway will handle all schema changes through versioned migration scripts.

### Entities

The application uses the following main entities:

- **Product**: Core product information with stock tracking
- **Category**: Product categorization and hierarchy
- **User**: User authentication and authorization
- **StockLog**: Historical record of stock changes

## 📁 Project Structure

```
stockmanager_be/
├── src/
│   ├── main/
│   │   ├── java/com/stockmanager/stockmanager_be/
│   │   │   ├── api/                    # 🎯 Presentation Layer
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── CategoryController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   └── UserController.java
│   │   │   │
│   │   │   ├── service/                # 💼 Business Logic Layer
│   │   │   │   ├── impl/
│   │   │   │   ├── ProductService.java
│   │   │   │   ├── CategoryService.java
│   │   │   │   ├── UserService.java
│   │   │   │   └── CustomUserDetailsService.java
│   │   │   │
│   │   │   ├── repo/                   # 💾 Data Access Layer
│   │   │   │   ├── ProductRepo.java
│   │   │   │   ├── CategoryRepo.java
│   │   │   │   └── UserRepo.java
│   │   │   │
│   │   │   ├── entity/                 # 📊 Domain Models
│   │   │   │   ├── Product.java
│   │   │   │   ├── Category.java
│   │   │   │   └── User.java
│   │   │   │
│   │   │   ├── dto/                    # 📦 Data Transfer Objects
│   │   │   │   ├── request/            # Incoming DTOs
│   │   │   │   ├── response/           # Outgoing DTOs
│   │   │   │   ├── ProductBulkDto.java
│   │   │   │   ├── ProductUpdateDto.java
│   │   │   │   └── CategoryDto.java
│   │   │   │
│   │   │   ├── mapper/                 # 🔄 Entity-DTO Mappers
│   │   │   │   ├── ProductMapper.java
│   │   │   │   ├── CategoryMapper.java
│   │   │   │   └── UserMapper.java
│   │   │   │
│   │   │   ├── exception/              # ⚠️ Exception Handling
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── ProductNotFoundException.java
│   │   │   │   ├── CategoryNotFoundException.java
│   │   │   │   ├── DuplicateCategoryException.java
│   │   │   │   ├── DuplicateEntityException.java
│   │   │   │   └── ValidationException.java
│   │   │   │
│   │   │   ├── config/                 # ⚙️ Configuration
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── WebConfig.java
│   │   │   │
│   │   │   ├── filter/                 # 🔐 Security Filters
│   │   │   ├── constant/               # 📋 Constants
│   │   │   │   ├── ApiUriConstants.java
│   │   │   │   ├── MessageConstant.java
│   │   │   │   └── CommonMessageConstant.java
│   │   │   │
│   │   │   ├── type/                   # 🏷️ Enums & Types
│   │   │   └── util/                   # 🛠️ Utility Classes
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── messages*.properties      # 🌐 i18n files
│   │       └── db/migration/             # 🗄️ Flyway migrations
│   │           ├── V2__create_stock_log.sql
│   │           └── V3__add_new_columns_to_product.sql
│   │
│   └── test/                             # 🧪 Test classes
│       └── java/com/stockmanager/stockmanager_be/
│
├── pom.xml                               # 📦 Maven configuration
└── README.md                             # 📖 This file
```

### Layer Responsibilities

| Layer | Responsibility | Technologies |
|-------|---------------|--------------|
| **API/Controller** | Handle HTTP requests, validate input, return responses | Spring MVC, REST |
| **Service** | Business logic, transaction management, orchestration | Spring Service |
| **Repository** | Database operations, query methods | Spring Data JPA |
| **Entity** | Domain model, database mapping | JPA, Hibernate |
| **DTO** | API contract, data validation | Bean Validation |
| **Mapper** | Object transformation | MapStruct |
| **Exception** | Centralized error handling | @ControllerAdvice |
| **Config** | Application configuration | Spring Configuration |
| **Filter** | Request/response interception | Servlet Filters |

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- **Nirasha Nelki** - [GitHub Profile](https://github.com/nirasha-nelki)

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- All contributors and supporters of this project

---

**Version:** 0.0.1-SNAPSHOT  
**Last Updated:** December 2025
