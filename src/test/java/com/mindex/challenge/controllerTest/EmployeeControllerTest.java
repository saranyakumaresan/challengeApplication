package com.mindex.challenge.controllerTest;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockEmployeeController;
    @MockBean
    private EmployeeService employeeService;

    private Employee testEmployee;

    @Before
    public void setup(){
        testEmployee = new Employee();
        testEmployee.setEmployeeId("123456");
        testEmployee.setFirstName("Vik");
        testEmployee.setDepartment("Management");
        testEmployee.setPosition("Manager");
    }
    @After
    public void teardown(){
        testEmployee = null;
    }

    @Test
    public void employeeControllerTest() throws Exception {

        // READ 200 OK
        when(employeeService.read("123456"))
                .thenReturn(testEmployee);
        this.mockEmployeeController.perform(get("/employee/123456"))
                .andDo(print()).andExpect(status().isOk());

        // WRITE 201 CREATED
        when(employeeService.create( Mockito.any(Employee.class)))
                .thenReturn(testEmployee);
        String requestBodyJson = "{" +
                "      \"employeeId\" : \"123456\",\n" +
                "      \"firstName\" : \"Vik\",\n" +
                "      \"lastName\" : \"Kumar\",\n" +
                "      \"position\" : \"Manager\",\n" +
                "      \"department\" : \"Management\"\n" +
                "    }";
        this.mockEmployeeController.perform(post("/employee")
                        .content(requestBodyJson).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON) )
                .andExpect(status().isOk());
    }


}
