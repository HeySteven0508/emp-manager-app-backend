package com.ulaps.employeemanager.dto;

public class SummaryReportDTO {

    private String id;
    private String name;
    private long duration;

    public SummaryReportDTO() {
    }

    public SummaryReportDTO(String id, String name, long duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "SummaryReportDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                '}';
    }
}
