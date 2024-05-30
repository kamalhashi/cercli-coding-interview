# Employee Service
The Employee Service is a Spring Boot application built with Kotlin that provides CRUD operations for managing employees and their holiday time off requests.


### Features
For further reference, please consider the following sections:

* Create, Get, Update and Get All CRUD operations for  employees
* Store employee, timeOffRequest in to  HQL memory DB
* Create holiday time off requests for employees 
* Check request category not overlapping 
* Initialize application with dummy employees and categories 
* Generates OpenAPI documentation with SpringDoc OpenAPI
* Implements unit tests with JUnit and Mockito

### Prerequisites
Before you begin, ensure you have met the following requirements:

* Java 11 or higher installed on your machine
* Gradle installed for building the project

### API Endpoints
* GET /employees/{employeeId}: Retrieve employee details by ID
* POST /employees: Create a new employee
* PUT /employees/{employeeId}: Update an existing employee
* DELETE /employees/{employeeId}: Delete an employee by ID
* POST /time-off-requests: Submit a holiday time off request for an employee
* GET /time-off-requests: Retrieve all holiday time off requests


###  Build the Project
 ```bash
    ./gradlew build 
 ```
###  Run the Application
 ```bash
    ./gradlew bootRun
 ```

### Testing
To run unit tests, execute the following command:

 ```bash
    ./gradlew test
 ```
### Accessing the HQL DB
HQL can be accessed at  http://localhost:8080/h2-console/ once the application is running.

### Additional Information
For more details on the endpoints and their usage, refer to the OpenAPI documentation available at http://localhost:8080/swagger-ui.html once the application is running.

### PENDING

* Add unit testing for overlapping time-off-request
* Add `curl request`  section in README.md  
