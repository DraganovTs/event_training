# EventTraining

A multi-module Java microservices project built with Spring Boot and Spring Cloud. It demonstrates a simple event-driven architecture using Axon Framework, service discovery with Eureka, API Gateway with Spring Cloud Gateway, and persistence with MySQL per service. 

The repository contains the following modules/services:
- eureka-server — Service discovery (Netflix Eureka)
- gateway-server — Edge/API gateway (Spring Cloud Gateway)
- user-service — CRUD and query endpoints for Users (Axon + JPA + MySQL)
- product-service — Product domain (Axon + JPA + MySQL)
- email-service — Email domain (Axon + JPA + MySQL)
- common — Shared DTOs and utilities between services


## Stack
- Language: Java (Spring Boot 3.x)
- Build: Maven (multi-module)
- Frameworks:
  - Spring Boot (Web, Data JPA, JDBC)
  - Spring Cloud Netflix (Eureka Server/Client), Spring Cloud Gateway
  - Axon Framework (axon-spring-boot-starter)
  - Lombok
  - Jakarta Validation
- Database: MySQL (one schema per service)
- Message/Events: Axon Server (configured at `localhost:8124`)

> Note: Spring Boot 3 requires Java 17+. Ensure your JDK is 17 or higher.


## Module ports and service names
Based on `application.yml`:
- eureka-server: `http://localhost:8070` (spring.application.name = `eureka-server`)
- gateway-server: `http://localhost:8080` (spring.application.name = `gateway-server`)
- user-service: `http://localhost:8081` (spring.application.name = `user-service`)
- product-service: `http://localhost:8082` (spring.application.name = `product-service`)
- email-service: `http://localhost:8083` (spring.application.name = `email-service`)

All services are configured as Eureka clients (except the Eureka server itself) and point to the Eureka registry at `http://localhost:8070/eureka/`.

Axon Server endpoint (for command/query/event messaging): `localhost:8124`.


## Requirements
- Java 17+
- Maven 3.9+
- MySQL 8.x accessible on `localhost:3306` (or adjust JDBC URLs)
- Axon Server running on `localhost:8124`

Optional:
- Docker (if you plan to containerize)
- Docker Compose (a `docker-compose.yml` file exists but is currently empty)


## Environment variables
The services expect database credentials via environment variables:
- `SQL_DB_USERNAME` — MySQL username with access to the service schemas
- `SQL_DB_PASSWORD` — MySQL password

You can export these variables in your shell before running the apps. On Windows PowerShell:
```powershell
$env:SQL_DB_USERNAME = "root"
$env:SQL_DB_PASSWORD = "your_password"
```

On Linux/macOS Bash:
```bash
export SQL_DB_USERNAME=root
export SQL_DB_PASSWORD=your_password
```


## Database schemas
Each service points to its own MySQL schema (database):
- user-service: `eventtraininguserservice`
- product-service: `eventtrainingproductservice`
- email-service: `eventtrainingemailservice`

Create them up front if you’re not using `ddl-auto: update` to create tables automatically:
```sql
CREATE DATABASE IF NOT EXISTS eventtraininguserservice;
CREATE DATABASE IF NOT EXISTS eventtrainingproductservice;
CREATE DATABASE IF NOT EXISTS eventtrainingemailservice;
```


## Setup and running locally
1) Start supporting infrastructure
- Start MySQL and ensure credentials in env vars are correct.
- Start Axon Server (default port 8124).
- Start Eureka Server (module `eureka-server`).

2) Build the project (root):
```bash
mvn -U -q -DskipTests package
```

3) Start modules (in separate terminals) using Maven:
- Eureka Server:
  ```bash
  mvn -pl eureka-server spring-boot:run
  ```
- API Gateway:
  ```bash
  mvn -pl gateway-server spring-boot:run
  ```
- User Service:
  ```bash
  mvn -pl user-service spring-boot:run
  ```
- Product Service:
  ```bash
  mvn -pl product-service spring-boot:run
  ```
- Email Service:
  ```bash
  mvn -pl email-service spring-boot:run
  ```

Alternatively, you can open each module in your IDE and run the `*Application` main class:
- `org.homemade.eureka.server.EurekaServerApplication`
- `org.homemade.gateway.server.GatewayServerApplication`
- `org.homemade.user.service.UserServiceApplication`
- `org.homemade.product.service.ProductServiceApplication`
- `org.homemade.email.service.EmailServiceApplication`


## API Gateway
The gateway is available at `http://localhost:8080`. Service routes are configured primarily via discovery (Eureka). The configuration sets `discovery.locator.enabled: false` currently, so explicit route configuration may be needed. Review `gateway-server/src/main/resources/application.yml` to adjust routing.


## Scripts and tooling
- Maven is used for all build/run tasks. No Maven Wrapper files are present in the repository at the moment.
- Docker Compose file exists at `docker-compose/docker-compose.yml` but is empty.

TODOs:
- [ ] Populate `docker-compose/docker-compose.yml` to run MySQL, Axon Server, Eureka, Gateway, and services.
- [ ] Add Maven Wrapper (`mvn -N io.takari:maven:wrapper`) for reproducible builds.
- [ ] Add route configuration to gateway or enable discovery locator.


## Tests
Standard Maven lifecycle is used.
- Run all tests for the whole repo:
  ```bash
  mvn test
  ```
- Run tests for a specific module and its dependencies:
  ```bash
  mvn -pl user-service -am test
  mvn -pl product-service -am test
  mvn -pl email-service -am test
  mvn -pl gateway-server -am test
  mvn -pl eureka-server -am test
  ```

If you need more verbose logs while testing, you can use:
```bash
mvn -DtrimStackTrace=false -Dsurefire.printSummary=true test
```


## Project structure
```
EventTraining/
├─ pom.xml                      # Root Maven aggregator (Spring Boot parent)
├─ eureka-server/
│  ├─ src/main/java/org/homemade/eureka/server/EurekaServerApplication.java
│  └─ src/main/resources/application.yml (port 8070)
├─ gateway-server/
│  ├─ src/main/java/org/homemade/gateway/server/GatewayServerApplication.java
│  └─ src/main/resources/application.yml (port 8080)
├─ user-service/
│  ├─ src/main/java/org/homemade/user/service/UserServiceApplication.java
│  └─ src/main/resources/application.yml (port 8081, MySQL, Axon, Eureka)
├─ product-service/
│  ├─ src/main/java/org/homemade/product/service/ProductServiceApplication.java
│  └─ src/main/resources/application.yml (port 8082, MySQL, Axon, Eureka)
├─ email-service/
│  ├─ src/main/java/org/homemade/email/service/EmailServiceApplication.java
│  └─ src/main/resources/application.yml (port 8083, MySQL, Axon, Eureka)
├─ common/                      # Shared DTOs/utilities
└─ docker-compose/
   └─ docker-compose.yml        # TODO: populate services
```


## Configuration highlights
- Eureka client configuration is enabled for gateway, user, product, and email services and points to `http://localhost:8070/eureka/`.
- JPA set to `hibernate.ddl-auto: update` for convenience in dev environments; consider `validate` or migrations for production.
- Axon server configured at `localhost:8124` (adjust if your Axon Server runs elsewhere).
- Logging level for `org.hibernate.SQL` set to `WARN` in `user-service` to reduce verbosity.


## License
TODO: Add license information (e.g., MIT, Apache-2.0). If unsure, include a `LICENSE` file at the repository root.
