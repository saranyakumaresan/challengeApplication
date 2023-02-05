package com.mindex.challenge.controllerTest;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReportingStructureControllerTest {

    @Autowired
    private MockMvc mockReportingController;
    private Employee testEmployee, directReportsEmployee1, directReportsEmployee2;

    @MockBean
    private ReportingStructureService service;
    @Before
    public void setup(){
        testEmployee = new Employee();
        directReportsEmployee1 = new Employee();
        directReportsEmployee2 = new Employee();
        testEmployee.setEmployeeId("123456");
        testEmployee.setFirstName("test");
        testEmployee.setLastName("Reports");
        directReportsEmployee1.setEmployeeId(UUID.randomUUID().toString());
        directReportsEmployee1.setFirstName("DR1");
        directReportsEmployee1.setLastName("Report1");
        directReportsEmployee2.setEmployeeId(UUID.randomUUID().toString());
        directReportsEmployee2.setFirstName("DR2");
        directReportsEmployee2.setLastName("Report2");

        List<Employee> directReports = new ArrayList<>();
        directReports.add(directReportsEmployee1);
        directReports.add(directReportsEmployee2);
        testEmployee.setDirectReports(directReports);
    }

    @After
    public void teardown(){
        testEmployee = null;
        directReportsEmployee1 = null;
        directReportsEmployee2 = null;
    }


    @Test
    public void reportingStructureControllerTest() throws Exception {
        when(service.read("123456"))
                .thenReturn(new ReportingStructure(testEmployee,2));
        this.mockReportingController.perform(get("/employee/reports/123456"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfReports",Matchers.equalTo(2)));
    }
}

