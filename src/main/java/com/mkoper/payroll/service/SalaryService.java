package com.mkoper.payroll.service;

import java.util.List;

import com.mkoper.payroll.dto.SalaryDto;
import com.mkoper.payroll.model.Salary;

public interface SalaryService {
    // get all salaries
    public List<SalaryDto> getAll();

    // get salary with given employee ID
    public SalaryDto getByEmployeeId(Long employeeId);

    // update salary with given employee ID
    public SalaryDto updateSalary(SalaryDto salaryDto);

    // save new salary to the db
    public Salary saveSalary(Salary salary);

    // delete from the db
    public void deleteSalary(Long employeeId);
}
