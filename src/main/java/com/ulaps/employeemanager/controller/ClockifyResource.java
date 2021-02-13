package com.ulaps.employeemanager.controller;


import com.ulaps.employeemanager.dto.ClockifyUsers;
import com.ulaps.employeemanager.dto.RangeDate;
import com.ulaps.employeemanager.dto.SummaryReportDTO;
import com.ulaps.employeemanager.services.ClockifyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/clockify")
public class ClockifyResource {

    private final ClockifyService clockifyService;

    public ClockifyResource(ClockifyService clockifyService) {
        this.clockifyService = clockifyService;
    }

    @GetMapping("/users/all")
    public List<ClockifyUsers> getAllUsers() throws IOException, InterruptedException {
        return clockifyService.getAllUsers();
    }

    @GetMapping("/users/time")
    public List<SummaryReportDTO> getAllTime(@RequestBody RangeDate rangeDate){
        return clockifyService.getAllTime(rangeDate);
    }

}
