package com.mkoper.payroll.dto;

public class DepartmentDto {
    private Long id;
    private String depratmentName;
    
    public DepartmentDto() {};

    public DepartmentDto(Long id, String depratmentName) {
        this.id = id;
        this.depratmentName = depratmentName;
    }

    public String getDepratmentName() {
        return depratmentName;
    }

    public void setDepratmentName(String depratmentName) {
        this.depratmentName = depratmentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
