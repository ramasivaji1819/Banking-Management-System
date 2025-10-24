# Banking-Management-System
The Banking Management System is a Java-based application that simulates basic banking operations such as managing customers, accounts, and transactions. It uses MySQL as the database and follows a layered architecture with DAO, Service, and Model classes. The project is built using Maven for easy dependency management.

# Features
  - Manage customer records (add, view, update, delete)
  - Create and manage bank accounts (deposit, withdraw, transfer)
  - Record and view transaction history
  - MySQL database integration using JDBC
  - Modular design with DAO-Service-Model structure
  - Built with Maven for portability and scalability

# Project Structure

 BankingManagementSystem/
│
├── pom.xml                        # Maven build configuration
├── sql/
│   └── schema.sql                 # Database schema (tables)
├── src/
│   └── main/java/com/example/bank/
│       ├── App.java               # Main entry point
│       ├── dao/                   # Data Access Objects
│       ├── model/                 # Entity classes (Customer, Account, Transaction)
│       ├── service/               # Business logic and operations
│       └── util/                  # Database connection and helper classes
└── target/                        # Compiled output

# Technologies Used
 | Component         | Technology              |
| ----------------- | ----------------------- |
| Language          | Java 17+                |
| Database          | MySQL 8.0               |
| Build Tool        | Maven                   |
| Architecture      | DAO-Service-Model       |
| IDE (Recommended) | IntelliJ IDEA / Eclipse |

## 🧪 Future Enhancements

- Add transaction history GUI using JSP

- Implement user authentication

- Add admin dashboard

## 🧑‍💻 Author

Developed by Rama Sivaji
