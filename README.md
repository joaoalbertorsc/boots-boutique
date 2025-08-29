# Boots Boutique API

Welcome to the Boots Boutique API, a RESTful service built with Java and Spring Boot to manage a boutique's boot inventory.

## ✨ Features

This API provides endpoints to manage boot inventory, allowing for standard CRUD (Create, Read, Update, Delete) operations.

### API Endpoints

*   **`GET /api/v1/boots`**: Retrieves a list of all boots in the inventory.
*   **`POST /api/v1/boots`**: Adds a new boot to the inventory.
*   **`DELETE /api/v1/boots/{id}`**: Deletes a specific boot by its ID.
*   **`PUT /api/v1/boots/{id}/quantity/increment`**: Increments the quantity of a specific boot.
*   **`PUT /api/v1/boots/{id}/quantity/decrement`**: Decrements the quantity of a specific boot.
*   **`GET /api/v1/boots/search`**: Searches for boots based on query parameters (`material` or `type`).
*   **`GET /api/v1/boots/types`**: Returns a list of all available `BootType` enums.

## 🛠️ Technologies Used

*   **Java 18**
*   **Spring Boot**: Core framework for building the application.
*   **Spring Web**: For creating RESTful endpoints.
*   **Spring Data JPA**: For database interaction.
*   **H2 Database Engine**: In-memory database for development.
*   **Maven**: Dependency management and build tool.
*   **JUnit 5**: Standard for unit testing in Java.
*   **Mockito**: For creating mock objects in tests.
*   **Spring Boot Test & MockMvc**: For integration and controller layer testing.

## 🏁 Getting Started

Follow these instructions to get a copy of the project up and running on your local machine.

### Prerequisites

*   Java Development Kit (JDK) 17 or newer.
*   Apache Maven.

### Running the Application

1.  Clone this repository to your local machine:
    ```bash
    git clone https://github.com/your-username/boots-boutique.git
    ```

2.  Navigate to the project root directory:
    ```bash
    cd boots-boutique
    ```

3.  Run the application using Maven:
    ```bash
    mvn spring-boot:run
    ```

4.  The server will start on `http://localhost:8080`.

## 💡 API Usage Examples

Here are a few examples of how to interact with the API using `curl`.

**Get all boots:**
```bash
curl -X GET http://localhost:8080/api/v1/boots
```

**Add a new boot:**
```bash
curl -X POST http://localhost:8080/api/v1/boots \
-H "Content-Type: application/json" \
-d '{
    "type": "CHELSEA",
    "size": 11.5,
    "material": "Suede",
    "quantity": 10,
    "bestSeller": false
}'
```

## 🤝 Contributing

Contributions are welcome! If you'd like to improve this project, please follow these steps:

1.  Fork the repository.
2.  Create a new branch for your feature (`git checkout -b feature/new-feature`).
3.  Make your changes and commit them (`git commit -m 'Add new feature'`).
4.  Push to your branch (`git push origin feature/new-feature`).
5.  Open a Pull Request.

## 📧 Contact

If you have any questions or suggestions, feel free to reach out at jaoalbertorsc@gmail.com.
