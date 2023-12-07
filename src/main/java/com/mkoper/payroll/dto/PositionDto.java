package com.mkoper.payroll.dto;

public class PositionDto {
    private String positionName;
    private DepartmentDto department;
    
    public PositionDto() {}
    
    public PositionDto(String positionName, DepartmentDto department) {
        this.positionName = positionName;
        this.department = department;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

}
