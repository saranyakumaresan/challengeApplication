package com.mindex.challenge.service.impl;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CompensationServiceImplTest {
    private String compensationUrl;
    private String compensationIdUrl;

    @Autowired
    private EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Compensation testCompensation;

    LocalDate effDate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/employee/compensation";
        compensationIdUrl = "http://localhost:" + port + "/employee/compensation/{id}";
        testCompensation = new Compensation();
        testCompensation.setEmployee(employeeRepository.findByEmployeeId("03aa1462-ffa9-4978-901b-7c001562cf6f"));
        testCompensation.setSalary(130000);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        formatter = formatter.withLocale(Locale.US);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        effDate = LocalDate.parse("02-21-2023", formatter);
        testCompensation.setEffectiveDate(effDate);
    }

    @After
    public void teardown(){
        compensationUrl = null;
        compensationUrl = null;
        testCompensation = null;
        effDate = null;
    }

    @Test
    public void testCreateReadUpdate() {


        // Create checks 201 created
        ResponseEntity<Compensation> createdCompensationEntity =
                restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class);
        assertEquals(HttpStatus.CREATED, createdCompensationEntity.getStatusCode());
        Compensation createdCompensation = createdCompensationEntity.getBody();
        assertNotNull(createdCompensation);
        assertCompensationEquivalence(testCompensation, createdCompensation);


        // Read checks 200 OK
        ResponseEntity<Compensation> readEmployeeCompensationEntity =
                restTemplate.getForEntity(compensationIdUrl, Compensation.class, "03aa1462-ffa9-4978-901b-7c001562cf6f");
        Compensation readEmployeeCompensation = readEmployeeCompensationEntity.getBody();
        assertNotNull(readEmployeeCompensation);
        assertCompensationEquivalence(createdCompensation, readEmployeeCompensation);

        //Read checks 404 Invalid Emp id
        ResponseEntity<Compensation> readCompensationInvalidId =
                restTemplate.getForEntity(compensationIdUrl, Compensation.class, UUID.randomUUID().toString());
        assertEquals(HttpStatus.NOT_FOUND, readCompensationInvalidId.getStatusCode());

        //Read record before created - 404 record not found
        ResponseEntity<Compensation> compensationRecordNotFound =
                restTemplate.getForEntity(compensationIdUrl, Compensation.class,"c0c2293d-16bd-4603-8e08-638a9d18b22c");
        assertEquals(HttpStatus.NOT_FOUND, compensationRecordNotFound.getStatusCode());


    }
      private static void assertCompensationEquivalence(Compensation expected, Compensation actual){
          assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
          assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
          assertEquals(expected.getSalary(),actual.getSalary(),0.0001);
        }


}
