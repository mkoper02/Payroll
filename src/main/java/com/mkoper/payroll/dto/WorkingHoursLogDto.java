package com.mkoper.payroll.dto;

public class WorkingHoursLogDto {
    private Long id;
    private Long employeeId;
    private Integer hoursWorked;
    private Integer year;
    private Integer month;
    
    public WorkingHoursLogDto() {}
    
    public WorkingHoursLogDto(Long id, Long employeeId, Integer hoursWorked, Integer year, Integer month) {
        this.id = id;
        this.employeeId = employeeId;
        this.hoursWorked = hoursWorked;
        this.year = year;
        this.month = month;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Integer hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
}