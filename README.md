Average Calculator Microservice
This project implements an Average Calculator microservice using Spring Boot. The microservice exposes a REST API endpoint /numbers/{numberid} to fetch numbers based on qualified number IDs ('p' for prime, 'f' for Fibonacci, 'e' for even, and 'r' for random numbers). It calculates the average of the numbers received from an external test server and maintains a sliding window of a configurable size.

Technologies Used
Java 17
Spring Boot 3.3.0
Maven
Project Structure
The project structure follows standard Maven conventions:
average-calculator-microservice/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── afford/
│   │   │           └── exam/
│   │   │               ├── controller/
│   │   │               │   └── HttpCalculatorController.java   # REST API endpoints
│   │   │               ├── service/
│   │   │                   ├─ HttpCalculatorService.java  # Service logic
│   │   │               
│   │   └── resources/
│   │       └── application.properties   # Application configuration
│   └── test/   # Test cases (if implemented)
├── pom.xml    # Maven dependencies and build configuration
└── README.md  # Project documentation (you are reading this)
API Endpoint
GET /numbers/{numberid}
Description: Retrieves numbers based on the specified number ID ('p', 'f', 'e', 'r').
Parameters:
numberid: Qualified number ID ('p' for prime, 'f' for Fibonacci, 'e' for even, 'r' for random numbers).
Response:
JSON format containing:
json
{
    "numbers": [6, 8],
    "windowPrevState": [2, 3, 5],
    "windowCurrState": [2, 3, 5, 6, 8],
    "avg": 5.00
}
numbers: Numbers received from the test server.
windowPrevState: Previous state of the sliding window.
windowCurrState: Current state of the sliding window after adding new numbers.
avg: Average of the current window numbers.
Configuration
Window Size: The size of the sliding window can be configured in application.properties.
External API: The microservice integrates with an external test server to fetch numbers.
Setup and Running the Application
Clone the repository:

bash
Copy code
git clone <repository_url>
cd average-calculator-microservice
Build the application:

bash
Copy code
mvn clean package
Run the application:

bash
Copy code
java -jar target/average-calculator-microservice.jar
Access the API:
Open a web browser or use tools like Postman to access the API endpoints at http://localhost:8080/numbers/{numberid}.

Dependencies
Spring Boot Starter Web
Jackson JSON Processor
Spring Boot DevTools (optional)
Testing
Unit tests can be implemented using JUnit and Mockito for testing controller and service components.
Integration tests can be added to verify interactions with the external API.
Notes
Ensure proper error handling for scenarios like invalid numberid or failures in fetching data from the external API.
The application should handle concurrent requests efficiently, ensuring thread safety where necessary.
