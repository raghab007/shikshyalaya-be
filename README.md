# 📚 Shikshyalaya – E-Learning Platform Backend

Welcome to the **Shikshyalaya** backend – a robust Spring Boot REST API designed for a full-fledged e-learning platform. It supports features like course management, user roles (student/tutor/admin), authentication, payments, and much more.

## 🛠 Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Security + JWT**
- **Spring Data JPA (Hibernate)**
- **PostgreSQL**
- **Maven**
- **Lombok**
- **Swagger/OpenAPI**
- **Payment Gateway (Khalti/)**

---

## ✨ Features

- 🔐 Secure login & registration (JWT based)
- 🧑‍🏫 Role-based access control (Admin, Tutor, Student)
- 🎓 Course creation, enrollment, and purchase
- 🧾 Video upload support 
- 💬 Chat and feedback
- 💳 Payment integration
- 📄 RESTful API for frontend integration

---

---

## ⚙️ Installation

### Prerequisites

- Java 17+
- Maven
- MySQL/PostgreSQL
- IDE (IntelliJ / VSCode)

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/your-username/shikshyalaya-backend.git

# 2. Navigate to project directory
cd shikshyalaya-backend

# 3. Configure database
# Edit src/main/resources/application.properties with your DB credentials

# 4. Build and run the project
mvn spring-boot:run


