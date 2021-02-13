package com.ulaps.employeemanager.dto;

import lombok.Data;

@Data
public class ClockifyUsers {
    private String id;
    private String email;
    private String name;

    public ClockifyUsers() {
    }

    public ClockifyUsers(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
