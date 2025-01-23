# Spring Boot Project - REST API

This project is a Spring Boot application that provides a REST API for Offshore ground sampler backend which consists of CRUD operations on locations and statistics.
It also includes basic authentication and configurable threshold values.
The project is ready to run and contains all necessary tests.

## Features
- REST API with CRUD operations for:
    - Locations endpoint
    - Samples endpoint
    - Statistics endpoint
- Threshold values configuration in application.yml
- Basic authentication for API security for POST, PUT and DELETE operations
- All tests are implemented for validation
- Postman collections for easy testing

## Project Setup

### Prerequisites
Before running the project, make sure you have the following installed:
- Java 17 or higher
- Maven (or Gradle)
- Postman (for testing APIs)

### Clone the Repository
```bash
git clone https://github.com/merlinchacko/offshore-ground-sampler.git
cd your-repository

Build the Project

To build the project, run the following command:

mvn clean install

Configure Threshold Values in application.yml

You can configure your threshold values by editing the application.yml file located in the src/main/resources directory.

Example:

ogs:
  sample-threshold:
    minWaterContentPercent: 5.0
    maxWaterContentPercent: 150.0
    minUnitWeightKNPerM3: 12.0

Run the Application

To run the application, execute:

mvn spring-boot:run

The application will start and be accessible at http://localhost:8081.

Authentication

Basic authentication is required to access the endpoints. You can use the following credentials:

Username: user

Password: password


Postman Collections

The Postman collections for the API endpoints are included in the postman folder. You can import them directly into Postman for testing.

API Endpoints

Locations Endpoint

GET /api/locations
Retrieve a list of locations.

GET /api/locations/{id}
Retrieve a location by ID.

POST /api/locations
Create a new location.

PUT /api/locations/{id}
Update an existing location.

DELETE /api/locations/{id}
Delete a location by ID.


Statistics Endpoint

GET /api/statistics
Retrieve a list of statistics.

GET /api/statistics/{id}
Retrieve a statistic by ID.

POST /api/statistics
Create a new statistic.

PUT /api/statistics/{id}
Update an existing statistic.

DELETE /api/statistics/{id}
Delete a statistic by ID.


Running Tests

To run all tests, use the following command:

mvn test

