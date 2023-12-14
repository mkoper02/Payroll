package com.mkoper.payroll.service;

import java.util.List;

import com.mkoper.payroll.dto.WorkingHoursLogDto;

public interface WorkingHoursLogService {
    // get list of all working hours log for employee with given ID
    public List<WorkingHoursLogDto> getWorkingLogsByEmployeeId(Long employeeId);

    // get list of working hours log for employee with given ID for given year
    public List<WorkingHoursLogDto> getWorkingLogByYear(Long employeeId, Integer year);
}
