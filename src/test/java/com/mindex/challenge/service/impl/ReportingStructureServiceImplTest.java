package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {
    private String employeeUrl;
    private String employeeReportsUrl;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee testEmployeeJohn, testEmployeeRingo;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeReportsUrl = "http://localhost:" + port + "/employee/reports/{id}";
        testEmployeeJohn = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        testEmployeeRingo = employeeRepository.findByEmployeeId("03aa1462-ffa9-4978-901b-7c001562cf6f");
    }

    @After
    public void tearDown() {
        employeeReportsUrl = null;
        testEmployeeJohn = null;
        testEmployeeRingo = null;
    }

    @Test
    public void testRead() {

        ReportingStructure testReportingStructure = new ReportingStructure(testEmployeeJohn, 4);
        ReportingStructure testReportingStructure2 = new ReportingStructure(testEmployeeRingo, 2);

        //Test 200 Ok

        ResponseEntity<ReportingStructure> reportingStructureResponseEntity =
                restTemplate.getForEntity(employeeReportsUrl,
                        ReportingStructure.class, testEmployeeJohn.getEmployeeId());
        assertEquals(HttpStatus.OK, reportingStructureResponseEntity.getStatusCode());

        ReportingStructure reportingStructure = reportingStructureResponseEntity.getBody();
        assertNotNull(reportingStructure);
        assertEmployeeEquivalence(testReportingStructure.getEmployee(), reportingStructure.getEmployee());
        assertEquals(testReportingStructure.getNumberOfReports(), reportingStructure.getNumberOfReports());

        ResponseEntity<ReportingStructure> reportingStructureResponseEntity1 =
                restTemplate.getForEntity(employeeReportsUrl,
                        ReportingStructure.class, testEmployeeRingo.getEmployeeId());
        assertEquals(HttpStatus.OK, reportingStructureResponseEntity.getStatusCode());

        ReportingStructure reportingStructure2 = reportingStructureResponseEntity1.getBody();
        assertNotNull(reportingStructure2);
        assertEmployeeEquivalence(testReportingStructure.getEmployee(),reportingStructure.getEmployee());
        assertEquals(testReportingStructure2.getNumberOfReports(), reportingStructure2.getNumberOfReports());

        //Test 404 Invalid Employee ID

        ResponseEntity<ReportingStructure> reportingStructureResponseEntity3 =
                restTemplate.getForEntity(employeeReportsUrl,
                        ReportingStructure.class, UUID.randomUUID().toString());
        assertEquals(HttpStatus.NOT_FOUND, reportingStructureResponseEntity3.getStatusCode());

    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

}
