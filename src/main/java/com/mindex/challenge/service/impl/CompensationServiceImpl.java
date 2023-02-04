package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.exception.BadRequestException;
import com.mindex.challenge.exception.ResourceNotFoundException;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);
    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Compensation read(String id){
        LOG.debug("Creating employee with id [{}]", id);
        Employee employee = employeeService.read(id);
        if (employee == null) {
            throw new ResourceNotFoundException("Invalid employeeId: " + id);
        }

       Compensation compensation = compensationRepository.findByEmployee(employee);

      if(compensation == null){
          throw new ResourceNotFoundException("Compensation record for the employee id::"+ id + " not found");
      }

        return compensation;


    }
    @Override
    public Compensation create(Compensation compensation){
        LOG.debug("Creating compensation  [{}]", compensation);
        try {
            String id = compensation.getEmployee().getEmployeeId();
            Employee employee = employeeService.read(id);
            if(employee == null){
                throw new ResourceNotFoundException("Invalid employee ID::" + id);
            }
            compensation.setEmployee(employee);

            compensationRepository.insert(compensation);

            return compensation;
        }
        catch(NullPointerException e){
            throw new BadRequestException("Missing request parameter employee ID " + e.getMessage());
        }


    }
}
