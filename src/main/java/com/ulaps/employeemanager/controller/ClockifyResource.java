package com.ulaps.employeemanager.controller;


import com.ulaps.employeemanager.dto.ClockifyUsers;
import com.ulaps.employeemanager.dto.RangeDate;
import com.ulaps.employeemanager.dto.SummaryReportDTO;
import com.ulaps.employeemanager.services.ClockifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/clockify")
public class ClockifyResource {

    private final ClockifyService clockifyService;

    @Autowired
    public ClockifyResource(ClockifyService clockifyService) {
        this.clockifyService = clockifyService;
    }



    @GetMapping("/users/all")
    public ResponseEntity<List<ClockifyUsers>> getAllUsers() throws IOException, InterruptedException {
        return new ResponseEntity<>(clockifyService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/users/time")
    public ResponseEntity<List<SummaryReportDTO>> getAllTime(@RequestBody RangeDate rangeDate){
        return new ResponseEntity<>(clockifyService.getAllTime(rangeDate), HttpStatus.OK);
    }

}
