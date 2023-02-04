package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.exception.ResourceNotFoundException;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Slf4j
@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Getting number of reports for employee with id [{}]", id);

        ReportingStructure reportingStructure = getNumberOfReports(id);

        return reportingStructure;
    }

    public ReportingStructure getNumberOfReports(String employeeId){
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        if (employee == null) {
            throw new ResourceNotFoundException("Invalid employeeId: " + employeeId);
        }
        int numberOfReports =0;
        List<Employee> directReports = employee.getDirectReports();

        if(directReports!=null){
            numberOfReports = directReports.size();
            numberOfReports += directReports.stream()
                    .map(emp -> employeeRepository.findByEmployeeId(emp.getEmployeeId()))
                    .filter(emp -> (emp.getDirectReports()) != null)
                    .mapToInt(emp -> emp.getDirectReports().size()).sum();

            mapEmployeeReportingStructure(directReports);
        }
        LOG.debug("Number of Reports", numberOfReports);


        return (new ReportingStructure(employee, numberOfReports));

    }

    private void mapEmployeeReportingStructure(List<Employee> directReports) {
        directReports.forEach(emp -> {
            Employee e = employeeRepository.findByEmployeeId(emp.getEmployeeId());
            emp.setFirstName(e.getFirstName());
            emp.setLastName(e.getLastName());
            emp.setDepartment(e.getDepartment());
            emp.setPosition(e.getPosition());
            emp.setDirectReports(e.getDirectReports());
            if(e.getDirectReports()!=null){
                mapEmployeeReportingStructure(e.getDirectReports());
            }
        });


    }

}
