# diaryProducts - Backend API

Spring Boot REST API for the **Lumina** dairy products e-commerce platform. This backend powers product management (CRUD + image upload), user/admin authentication, and order placement with automatic stock deduction.

## Tech Stack

| Component       | Technology                            |
|-----------------|---------------------------------------|
| Language        | Java 21                               |
| Framework       | Spring Boot 4.1.1                     |
| Persistence     | Spring Data JPA (Hibernate)           |
| Database        | MySQL                                 |
| Build           | Maven                                 |
| Docs            | Jakarta Validation                    |

## Prerequisites

- JDK 21+
- Maven (or use the bundled `mvnw` wrapper)
- MySQL server running locally

## Getting Started

### 1. Create the database

```sql
CREATE DATABASE Diary_products;
```

### 2. Configure the connection

Edit `src/main/resources/application.properties` and match your MySQL credentials:

```properties
spring.datasource.url = jdbc:mysql://localhost:3306/Diary_products
spring.datasource.username = root
spring.datasource.password = root
spring.jpa.hibernate.ddl-auto = update
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

> `ddl-auto = update` will auto-create/update the tables on startup.

### 3. Run the application

```bash
# Using the Maven wrapper (Windows)
mvnw.cmd spring-boot:run

# Using the Maven wrapper (Unix)
./mvnw spring-boot:run

# Or with Maven directly
mvn spring-boot:run
```

The API starts at: **http://localhost:8080**

### 4. Compile / Test

```bash
mvn compile        # compile only
mvn test           # run tests
mvn package        # build a jar (target/diaryProducts-0.0.1-SNAPSHOT.jar)
```

## Project Structure

```
src/main/java/com/example/diaryProducts/
├── DiaryProductsApplication.java   # Main entry point
├── config/
│   └── CorsConfig.java             # CORS setup (allows frontend origins + *)
├── controller/
│   ├── DiaryController.java        # Products + orders endpoints
│   ├── UserController.java         # User register/login
│   └── AdminController.java        # Admin register/login + user list
├── model/
│   ├── Products.java               # Product entity (incl. image BLOB)
│   ├── Users.java                  # User entity
│   ├── Admin.java                  # Admin entity
│   └── orders.java                 # Order entity (product_id, quantity)
├── repository/
│   ├── ProductsRepo.java           # Product data access
│   ├── UsersRepo.java
│   ├── AdminRepo.java
│   └── orderRepo.java
└── service/
    ├── ProductService.java         # Product + order business logic
    ├── UserService.java
    └── AdminService.java
```

## Database Models

### Products
| Field            | Type     | Notes            |
|------------------|----------|------------------|
| `product_id`     | int (PK) | Auto-increment   |
| `product_name`   | String   |                  |
| `description`    | String   |                  |
| `price`          | double   |                  |
| `stock_quantity` | int      |                  |
| `image`          | LONGBLOB | Binary image     |
| `image_type`     | String   | e.g. `image/png` |

### orders
| Field           | Type     | Notes                          |
|-----------------|----------|--------------------------------|
| `orderid`       | int (PK) | Auto-increment                 |
| `customer_name` | String   |                                |
| `phone`         | long     |                                |
| `email`         | String   |                                |
| `address`       | String   |                                |
| `total_price`   | double   | Computed by backend            |
| `product_id`    | int      | Product being ordered          |
| `quantity`      | int      | Quantity (used for stock)      |

### Users
`user_id` (PK), `username`, `email`, `password`

### Admin
`admin_id` (PK), `username`, `password`

## API Endpoints

All routes are prefixed with the base URL `http://localhost:8080`.

### Products

| Method | Endpoint                            | Description                                        | Body / Params                                     |
|--------|-------------------------------------|----------------------------------------------------|---------------------------------------------------|
| GET    | `/products`                         | List all products                                  | —                                                 |
| POST   | `/createProduct`                    | Create a product (JSON)                            | JSON `Products`                                   |
| POST   | `/createProductWithImage`           | Create a product with an uploaded image            | multipart: `productName`, `price`, `stock_quantity`, `description` (opt), `image` (file, opt) |
| PUT    | `/update/{id}`                      | Update name/description/price/stock                | JSON `Products`                                    |
| PUT    | `/updateStock/{product_id}`         | Update stock quantity only                         | query `stock_quantity`                             |
| PUT    | `/updateImage/{product_id}`         | Update a product's image                           | multipart: `image` (file)                          |
| DELETE | `/deleteProduct/{product_id}`       | Delete a product                                   | —                                                 |
| GET    | `/image/{product_id}`               | Serve the product image as binary bytes            | —                                                 |
| GET    | `/filterByName?product_name=X`      | Filter products by name (case-insensitive)         | query `product_name`                               |
| GET    | `/filterByPrice?minPrice=X&maxPrice=Y` | Filter by price range                           | query `minPrice`, `maxPrice`                       |
| GET    | `/sortByPriceAsc`                   | Sort products by price ascending                   | —                                                 |
| GET    | `/sortByPriceDesc`                  | Sort products by price descending                  | —                                                 |

### Orders

| Method | Endpoint       | Description                                              | Body / Params                          |
|--------|----------------|----------------------------------------------------------|----------------------------------------|
| GET    | `/orderDetails`| List all orders                                          | —                                      |
| POST   | `/createorders`| Place an order — **deducts stock** and computes total     | JSON with `customer_name`, `phone`, `email`, `address`, `product_id`, `quantity`, `total_price` (use `0`) |

> **Ordering & stock:** the `POST /createorders` handler validates sufficient stock, decreases `Products.stock_quantity` by the ordered `quantity`, and sets `order.total_price = product.price * quantity` automatically.

### Users

| Method | Endpoint      | Description                    | Body                 |
|--------|---------------|--------------------------------|----------------------|
| POST   | `/userRegister`| Register a new user           | JSON `{username, email, password}` |
| POST   | `/userLogin`  | Login a user                   | JSON `{username, password}` → returns `"success"`/`"failure"` |

### Admin

| Method | Endpoint        | Description                  | Body / Notes                        |
|--------|-----------------|------------------------------|-------------------------------------|
| POST   | `/adminRegister`| Register a new admin         | JSON `{username, password}`         |
| POST   | `/adminLogin`   | Login an admin               | JSON `{username, password}` → returns `"success"`/`"failure"` |
| GET    | `/admin/allUsers`| List all registered users   | —                                   |

## CORS

The backend is configured to allow:
- `http://localhost:3000`
- `http://localhost:5500`
- `http://127.0.0.1:3000`
- `http://127.0.0.1:5500`
- any origin (`*`) via `addAllowedOriginPattern("*"`)

All standard HTTP methods (GET, POST, PUT, DELETE, OPTIONS) and headers are permitted, with credentials enabled.

## Notes

- Passwords are stored and compared in plain text for simplicity (not recommended for production).
- Product images are stored as binary BLOBs in MySQL and served back via `GET /image/{product_id}`.
- The frontend for this backend lives in the `dairy-products-frontend/` directory at the project root.
