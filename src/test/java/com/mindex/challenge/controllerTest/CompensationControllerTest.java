package com.mindex.challenge.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.ReportingStructureService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestBody;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc


public class CompensationControllerTest {
    @Autowired
    private MockMvc mockCompensationController;
    private Employee testEmployee, directReportsEmployee1, directReportsEmployee2;
    LocalDate effDate;
    private Compensation testCompensation;
//    private String compensationResponseBody;

    @MockBean
    private CompensationService compensationService;
    @Before
    public void setup(){
        testEmployee = new Employee();
        testEmployee.setEmployeeId("123456");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        formatter = formatter.withLocale(Locale.US);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        effDate = LocalDate.parse("02-21-2023", formatter);
        testCompensation = new Compensation(testEmployee, 200000, effDate);
    }

    @Test
    public void compensationControllerTest() throws Exception {

        // READ 200 OK
        when(compensationService.read("123456"))
                .thenReturn(testCompensation);
        this.mockCompensationController.perform(get("/employee/compensation/123456"))
                .andDo(print()).andExpect(status().isOk());

        when(compensationService.create( Mockito.any(Compensation.class)))
                .thenReturn(testCompensation);

        // WRITE 201 CREATED
        testCompensation = new Compensation(testEmployee, 120000, null);
        String body = (new ObjectMapper()).writeValueAsString(testCompensation);
        this.mockCompensationController.perform(post("/employee/compensation")
                        .contentType(MediaType.APPLICATION_JSON) .content(body))
               .andExpect(status().isCreated());
    }

}
