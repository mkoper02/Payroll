package com.mkoper.payroll.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.dto.WorkingHoursLogDto;
import com.mkoper.payroll.model.WorkingHoursLog;
import com.mkoper.payroll.repository.WorkingHoursLogRepository;
import com.mkoper.payroll.service.WorkingHoursLogService;

@Service
public class WorkingHoursLogServiceImpl implements WorkingHoursLogService {

    @Autowired
    private WorkingHoursLogRepository workingHoursLogRepository;

    public WorkingHoursLogServiceImpl(WorkingHoursLogRepository workingHoursLogRepository) {
        this.workingHoursLogRepository = workingHoursLogRepository;
    }

    @Override
    public List<WorkingHoursLogDto> getWorkingLogsByEmployeeId(Long employeeId) {
        List<WorkingHoursLog> workingLogs = workingHoursLogRepository.findByEmployeeId(employeeId);
        return workingLogs.stream().map((workingLog) -> mapToWorkingHoursLogDto(workingLog)).collect(Collectors.toList());
    }

    @Override
    public List<WorkingHoursLogDto> getWorkingLogByYear(Long employeeId, Integer year) {
        List<WorkingHoursLog> workingLogs = workingHoursLogRepository.findByDateBetweenAndEmployeeId(LocalDate.of(year, 01, 01), LocalDate.of(year, 12, 31), employeeId);
        return workingLogs.stream().map((workingLog) -> mapToWorkingHoursLogDto(workingLog)).collect(Collectors.toList());
    }
    
    private WorkingHoursLogDto mapToWorkingHoursLogDto(WorkingHoursLog workingLog) {
        WorkingHoursLogDto workingLogDto = new WorkingHoursLogDto();

        workingLogDto.setId(workingLog.getId());
        workingLogDto.setHoursWorked(workingLog.getHoursWorked());
        workingLogDto.setDate(workingLog.getDate());

        return workingLogDto;
    }

}
