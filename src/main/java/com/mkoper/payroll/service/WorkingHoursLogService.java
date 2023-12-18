package com.mkoper.payroll.service;

import java.util.List;

import com.mkoper.payroll.dto.DateDto;
import com.mkoper.payroll.dto.WorkingHoursLogDto;

public interface WorkingHoursLogService {
    // get list of all working hours log for employee with given ID
    public List<WorkingHoursLogDto> getWorkingLogsByEmployeeId(Long employeeId);

    // get list of working hours log for employee with given ID for given year
    public List<WorkingHoursLogDto> getWorkingLogByYear(Long employeeId, Integer year);

    // save new working log for employee with given ID
    public WorkingHoursLogDto saveWorkingLogForEmployee(WorkingHoursLogDto workingHoursLogDto);

    // create and save new working logs for all employees for given date
    public List<WorkingHoursLogDto> saveWorkingLogsForAll(DateDto date);

    // update working log from given date and for employee with given ID
    public WorkingHoursLogDto updateWorkLog(WorkingHoursLogDto workingHoursLogDto);

    // delete working log with given ID
    public void deleteWorkingLog(Long workingLogId);
}
