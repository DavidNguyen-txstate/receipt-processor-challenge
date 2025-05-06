# Receipt Processor API Documentation
This document provides an overview of the Receipt Processor API, including its functionality, usage, and setup instructions.

## Overview
The Receipt Processor API is designed to process receipts and calculate points based on the items purchased. It provides endpoints for uploading receipts, retrieving processed data, and calculating points.

## Implementation Details
The API is implemented using Spring Boot and follows RESTful principles. The main components include:
- **ReceiptController**: Handles incoming requests and routes them to the appropriate service methods.
  - **Endpoints**:
    - `POST /receipts/process`: Accepts a receipt in JSON format and processes it. The request body should contain the receipt details, including retailer, purchase date, purchase time, items, and total amount.
      - Map<String, String> processReceipt(@RequestBody Receipt receipt): Processes the receipt and returns a unique ID for the processed receipt.
    - `GET /receipts/{id}/points`: Retrieves the points associated with a processed receipt by its ID.
      - Map<String, Integer> getPoints(@PathVariable String id): Retrieves the points for the given receipt ID
- **ReceiptService**: Contains the business logic for processing receipts and calculating points.
  - **Methods**:
    - String processReceipt(Receipt receipt): Processes the receipt and stores it in the ReceiptStorage.
    - int getPoints(String id): Retrieves the points associated with a processed receipt by its ID.
- **ReceiptStorage**: Manages the storage of receipts and their associated points.
  - **Methods**:
    - void save(String id, int points): Saves the points associated with a receipt ID.
    - Integer getPoints(String id): Retrieves the points associated with a receipt ID.
- **Receipt**: Represents a receipt with its items and points.
  - **Fields**:
    - String retailer: The name of the retailer.
    - String purchaseDate: The date of purchase.
    - String purchaseTime: The time of purchase.
    - List<Item> items: A list of items purchased.
    - double totalAmount: The total amount spent on the receipt.
  - **Methods**:
    - void setRetailer(String retailer): Sets the retailer name.
    - void setPurchaseDate(String purchaseDate): Sets the purchase date.
    - void setPurchaseTime(String purchaseTime): Sets the purchase time.
    - void setItems(List<Item> items): Sets the list of items purchased.
    - void setTotal(String total): Sets the total amount spent.
    - String getRetailer(): Gets the retailer name.
    - String getPurchaseDate(): Gets the purchase date.
    - String getPurchaseTime(): Gets the purchase time.
    - List<Item> getItems(): Gets the list of items purchased.
    - String getTotal(): Gets the total amount spent.
- **Item**: Represents an item in a receipt with its name and price.
  - **Fields**:
    - String name: The name of the item.
    - double price: The price of the item.
  - **Methods**:
    - void setName(String name): Sets the item name.
    - void setPrice(double price): Sets the item price.
    - String getName(): Gets the item name.
    - double getPrice(): Gets the item price.
- **ReceiptsControllerTests**: Contains unit tests for the ReceiptController to ensure its functionality and correctness.
  - **Tests**:
    - void processReceipt_ReturnsId(): Tests the processReceipt method to ensure it returns a valid ID.
    - void getPoints_ReturnsPoints(): Tests the getPoints method to ensure it returns the correct points for a given receipt ID.
    - void getPoints_NotFound(): Tests the getPoints method to ensure it handles cases where the receipt ID is not found.
- **ReceiptServiceTests**: Contains unit tests for the ReceiptService to validate the business logic and calculations.
  - **Tests**:
    - void setUp(): Sets up the test environment and initializes the ReceiptService and ReceiptStorage.
    - void testProcessReceipt_withExample1(): Tests the processReceipt method with a sample receipt and verifies the points calculation.
    - void testProcessReceipt_withExample2(): Tests the processReceipt method with another sample receipt and verifies the points calculation.
- **ReceiptStorageTests**: Contains unit tests for the ReceiptStorage to verify the storage and retrieval of receipts.
  - **Tests**:
    - void testSaveAndGetPoints(): Tests the save and getPoints methods to ensure they work correctly.
    - void testGetPointsForUnknownId(): Tests the getPoints method to ensure it handles cases where the receipt ID is not found.
- **ReceiptProcessorApplicationTests**: Contains integration tests for the entire application to ensure all components work together as expected.
  - **Tests**:
    - void contextLoads(): Tests if the application context loads successfully.
    - void receiptControllerIsLoaded(): Tests if the ReceiptController bean is loaded in the application context.
    - void receiptServiceIsLoaded(): Tests if the ReceiptService bean is loaded in the application context.
    - void receiptStorageIsLoaded(): Tests if the ReceiptStorage bean is loaded in the application context.
- **ReceiptProcessorApplication**: The main entry point for the Spring Boot application.
  - **Method**:
    - public static void main(String[] args): Starts the Spring Boot application.

### Technologies Used
- Java 24
- Spring Boot 3.4.5
- Maven 3.9.4
- Lombok 1.18.28
- JUnit 5.10.0
- Mockito 5.5.0, or utilize the maven wrapper (mvnw, or mvnw.cmd for Windows)
- Docker 24.0.2

## Dockerized Setup

### Build the Docker image

docker build -t receipt-processor:latest .

### Run the Docker container

docker run -p 8080:8080 receipt-processor:latest

### API Endpoints

- Process Receipt: `POST http://localhost:8080/receipts/process`
- Get Points: `GET http://localhost:8080/receipts/{id}/points`

No additional configuration is required. All dependencies are handled by the Maven wrapper and the Docker build process.

### Troubleshooting
- Java 24 Compatibility: The Dockerfile uses eclipse-temurin:24-jdk-alpine for both build and run stages. Ensure your local environment is compatible with Java 24. If you encounter issues, consider using a different base image or Java version.
- Dockerfile Location: Ensure the Dockerfile is in the root directory of this project. The build context should be set to the root directory when running the Docker build command.
- Docker Build Context: When building the Docker image, ensure you are in the root directory of the project. The command should be run from the same directory where the Dockerfile is located.
- Docker Build Command: Use the command `docker build -t receipt-processor:latest .` to build the Docker image. The `.` at the end specifies the current directory as the build context.
- Docker Run Command: Use the command `docker run -p 8080:8080 receipt-processor:latest` to run the Docker container. This maps port 8080 on your host machine to port 8080 in the container.
- Docker Container Logs: If you encounter issues while running the container, check the logs using `docker logs <container_id>` to see any error messages or stack traces.
- Docker Container Status: Use `docker ps` to check if the container is running. If it is not running, use `docker ps -a` to see all containers, including stopped ones, and check their status.
- Docker Installation: Ensure Docker is installed and running on your machine. You can check the installation by running `docker --version` in your terminal.
- Docker Permissions: If you encounter permission issues while running Docker commands, ensure your user has the necessary permissions to run Docker. You may need to add your user to the `docker` group or run commands with `sudo` (Linux/Mac).
- Docker Cleanup: If you encounter issues with existing images or containers, consider cleaning up unused images and containers using `docker system prune` or `docker rm -f <container_id>` to remove specific containers.
- No Maven Needed: The Maven wrapper (mvnw) is used inside the container; Maven does not need to be installed on your machine.
- Port 8080: If you want to use a different port, change the left side of the -p flag (e.g., -p 8000:8080).
- API Testing: Use tools like Postman or curl to test the API endpoints.
- API Documentation: Refer to the API documentation for details on request and response formats, including example payloads for processing receipts and retrieving points.
- API Error Handling: The API returns appropriate HTTP status codes and error messages for invalid requests or processing errors. Ensure to handle these cases in your client application.

## Running the Application
To run the application, you can use the following command:

```bash
mvn spring-boot:run
```

or if you have Docker installed, you can build and run the Docker image as described above

or use the Maven wrapper to build and run the application:

```bash
mvnw spring-boot:run
```

or for Windows:

```bash
mvnw.cmd spring-boot:run
```

or go straight to ReceiptProcessorApplication.java and run it as a Java application.
This will start the application on port 8000 by default.