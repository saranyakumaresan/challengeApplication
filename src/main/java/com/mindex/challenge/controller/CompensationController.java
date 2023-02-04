package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.CompensationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@Slf4j
public class CompensationController {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);
    @Autowired
    private CompensationService compensationService;

    @GetMapping("/employee/compensation/{id}")
    public ResponseEntity<Compensation> read(@PathVariable String id){
        LOG.debug("Received compensation read request for id [{}]", id);
        return ResponseEntity.ok(compensationService.read(id));
    }

    @PostMapping("/employee/compensation")
    public ResponseEntity<Compensation> create(@RequestBody @NotNull Compensation compensation){

        LOG.debug("Received compensation create request with fields [{}]", compensation);

        return new ResponseEntity<>(compensationService.create(compensation), HttpStatus.CREATED);
    }
}
