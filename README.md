# Coding Challenge
## What's Provided
A simple [Spring Boot](https://projects.spring.io/spring-boot/) web application has been created and bootstrapped 
with data. The application contains information about all employees at a company. On application start-up, an in-memory 
Mongo database is bootstrapped with a serialized snapshot of the database. While the application runs, the data may be
accessed and mutated in the database without impacting the snapshot.

### How to Run
The application may be executed by running `gradlew bootRun`.

### How to Use
The following endpoints are available to use:
```
* CREATE
    * HTTP Method: POST 
    * URL: localhost:8080/employee
    * PAYLOAD: Employee
    * RESPONSE: Employee
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/employee/{id}
    * RESPONSE: Employee
* UPDATE
    * HTTP Method: PUT 
    * URL: localhost:8080/employee/{id}
    * PAYLOAD: Employee
    * RESPONSE: Employee
```
The Employee has a JSON schema of:
```json
{
  "type":"Employee",
  "properties": {
    "employeeId": {
      "type": "string"
    },
    "firstName": {
      "type": "string"
    },
    "lastName": {
          "type": "string"
    },
    "position": {
          "type": "string"
    },
    "department": {
          "type": "string"
    },
    "directReports": {
      "type": "array",
      "items" : "string"
    }
  }
}
```
For all endpoints that require an "id" in the URL, this is the "employeeId" field.

## What to Implement
Clone or download the repository, do not fork it.

### Task 1
Create a new type, ReportingStructure, that has two properties: employee and numberOfReports.

For the field "numberOfReports", this should equal the total number of reports under a given employee. The number of 
reports is determined to be the number of directReports for an employee and all of their distinct reports. For example, 
given the following employee structure:
```
                    John Lennon
                /               \
         Paul McCartney         Ringo Starr
                               /        \
                          Pete Best     George Harrison
```
The numberOfReports for employee John Lennon (employeeId: 16a596ae-edd3-4847-99fe-c4518e82c86f) would be equal to 4. 

This new type should have a new REST endpoint created for it. This new endpoint should accept an employeeId and return 
the fully filled out ReportingStructure for the specified employeeId. The values should be computed on the fly and will 
not be persisted.

### Approach for Task 1
    - REST endpoint to read ReportingStructure : /employee/reports/{id}
    - getNumberOfReports(args) -> returns the number of Reports for specified employee ID(total of direct and distinct)
    - mapEmployeeReportingStructure(args) -> To populate the reportingStructure tree in the response
    - Exception is thrown if Invalid employee ID is provided

### Task 2
Create a new type, Compensation. A Compensation has the following fields: employee, salary, and effectiveDate. Create 
two new Compensation REST endpoints. One to create and one to read by employeeId. These should persist and query the 
Compensation from the persistence layer.

### Approach for Task 2
    REST endpoints created:
        - CREATE: /employee/compensation
           - All the fields mentioned are passed as RequestBody
            - Returns 201 Status code if record is successfully created
            - Exceptions are thrown if employee field is missing or if invalid ID is provided 
            - Based on the scenario error code changes
            - HTTP status 404 with error code 1001 if invalid ID is provided
            - HTTP status 404 with error code 1003 for missing parameter
        - READ: /employee/compensation/{id}
            - Exception thrown for Invalid ID
            - Returns 404 with error code 1001

### Error codes and Error Messages
    Error codes and Error messages are created to customise the response returned for an exception
    The Error message has format as below:
        {
            "status": 404,
            "errorCode": "1001 The resource requested is not found",
            "message": "Invalid employeeId:  ",
            "description": "uri=/employee/compensation"
        }
### Exception Handler
    Custom Exceptions are created - ResourceNotFoundException with error codes
    Global Exception Handler - To handle all other exceptions - HttpStatus 500 with error code 1002

## Delivery
Please upload your results to a publicly accessible Git repo. Free ones are provided by Github and Bitbucket.
