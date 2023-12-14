package com.mkoper.payroll.service;

import java.util.List;

import com.mkoper.payroll.dto.SalaryDto;

public interface SalaryService {
    // get all salaries
    public List<SalaryDto> getAll();

    // get salary with given employee ID
    public SalaryDto getByEmployeeId(Long employeeId);

    // update salary with given employee ID
    public SalaryDto updateSalary(SalaryDto salaryDto, Long employeeId);

    // save new salary to the db
    public SalaryDto saveSalary(SalaryDto salaryDto);

    // delete from the db
    public void deleteSalary(Long employeeId);
}
