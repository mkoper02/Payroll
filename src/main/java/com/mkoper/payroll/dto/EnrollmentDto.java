package com.mkoper.payroll.dto;

public class EnrollmentDto {
    private Long id;
    private String contract_type;
    private Integer hours;

    public EnrollmentDto() {}
    
    public EnrollmentDto(Long id, String contract_type, Integer hours) {
        this.id = id;
        this.contract_type = contract_type;
        this.hours = hours;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getContractType() {
        return contract_type;
    }

    public void setContractType(String contract_type) {
        this.contract_type = contract_type;
    }

    public Integer getHours() {
        return hours;
    }
    public void setHours(Integer hours) {
        this.hours = hours;
    }
}
