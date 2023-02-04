package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@AllArgsConstructor
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);
    @Autowired
    private ReportingStructureService reportingStructureService;
    @GetMapping("/employee/reports/{id}")
    public ResponseEntity<ReportingStructure> getNumberOfReports(@PathVariable String id){

        LOG.debug("Received reportingStructure read request for id [{}] ", id);

        return ResponseEntity.ok(reportingStructureService.read(id));
    }
}
