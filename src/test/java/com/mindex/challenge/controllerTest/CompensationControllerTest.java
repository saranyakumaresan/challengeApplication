package com.mindex.challenge.controllerTest;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.exception.BadRequestException;
import com.mindex.challenge.service.CompensationService;
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
import java.time.LocalDate;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompensationControllerTest {
    @Autowired
    private MockMvc mockCompensationController;
    private Employee testEmployee, directReportsEmployee1, directReportsEmployee2;
    private Compensation testCompensation;

    @MockBean
    private CompensationService compensationService;
    @Before
    public void setup(){
        testEmployee = new Employee();
        testEmployee.setEmployeeId("123456");
        testCompensation = new Compensation(testEmployee, 200000, LocalDate.now());
    }
    @After
    public void teardown(){
        testEmployee = null;
        testCompensation = null;
    }

    @Test
    public void compensationControllerTest() throws Exception {

        // READ 200 OK
        when(compensationService.read("123456"))
                .thenReturn(testCompensation);
        this.mockCompensationController.perform(get("/employee/compensation/123456"))
                .andDo(print()).andExpect(status().isOk());

        // WRITE 201 CREATED
        when(compensationService.create( Mockito.any(Compensation.class)))
                .thenReturn(testCompensation);
        String requestJson = "{\"employee\": {\n" +
                "      \"employeeId\" : \"123456\",\n" +
                "      \"firstName\" : \"Sara\",\n" +
                "      \"lastName\" : \"Kumar\",\n" +
                "      \"position\" : \"SW Dev\",\n" +
                "      \"department\" : \"Engineering\"\n" +
                "    },\n" +
                "    \"salary\": 200000,\n" +
                "    \"effectiveDate\": \"02/04/2023\"}";
        this.mockCompensationController.perform(post("/employee/compensation")
                        .content(requestJson).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON) )
                .andExpect(status().isCreated());

        //400 BadRequest Exception
        when(compensationService.create( Mockito.any(Compensation.class)))
                .thenThrow( new BadRequestException("Mock 400"));
        this.mockCompensationController.perform(post("/employee/compensation")
                        .content(requestJson).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON) )
                .andExpect(status().isBadRequest());

        //500 Internal Server Error
        this.mockCompensationController.perform(post("/employee/compensation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON) )
                .andExpect(status().is5xxServerError());
    }

}
