# Wells Fargo JPA Backend

This project implements a financial portfolio management data model using Spring Boot and Java Persistence API (JPA).

## Entities

- FinancialAdvisor
- Client
- Portfolio
- Security

## Relationships

- FinancialAdvisor -> Client (One-to-Many)
- Client -> Portfolio (One-to-One)
- Portfolio -> Security (One-to-Many)

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Maven

## Project Structure

src/main/java/com/wellsfargo
├── model
│   ├── FinancialAdvisor.java
│   ├── Client.java
│   ├── Portfolio.java
│   └── Security.java
├── repository
│   ├── FinancialAdvisorRepository.java
│   ├── ClientRepository.java
│   ├── PortfolioRepository.java
│   └── SecurityRepository.java

## Author

Created as part of the Wells Fargo Software Engineering Job Simulation.
