package com.mkoper.payroll.dto;

import java.time.LocalDate;

public class WorkingHoursLogDto {
    private Long id;
    private Integer hoursWorked;
    private LocalDate date;
    
    public WorkingHoursLogDto() {}

    public WorkingHoursLogDto(Long id, Integer hoursWorked, LocalDate date) {
        this.id = id;
        this.hoursWorked = hoursWorked;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Integer hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}