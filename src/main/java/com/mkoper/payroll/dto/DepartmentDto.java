package com.mkoper.payroll.dto;

public class DepartmentDto {
    private String depratmentName;
    
    public DepartmentDto() {};

    public DepartmentDto(String depratmentName) {
        this.depratmentName = depratmentName;
    }

    public String getDepratmentName() {
        return depratmentName;
    }

    public void setDepratmentName(String depratmentName) {
        this.depratmentName = depratmentName;
    }
}
