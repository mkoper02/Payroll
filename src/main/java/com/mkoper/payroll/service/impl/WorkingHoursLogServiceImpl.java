package com.mkoper.payroll.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.dto.DateDto;
import com.mkoper.payroll.dto.PayrollRaportDto;
import com.mkoper.payroll.dto.WorkingHoursLogDto;
import com.mkoper.payroll.exceptions.InvalidDataGivenException;
import com.mkoper.payroll.exceptions.LastMonthRaportExistsException;
import com.mkoper.payroll.exceptions.ObjectNotFoundException;
import com.mkoper.payroll.model.WorkingHoursLog;
import com.mkoper.payroll.repository.EmployeeRepository;
import com.mkoper.payroll.repository.WorkingHoursLogRepository;
import com.mkoper.payroll.service.PayrollRaportService;
import com.mkoper.payroll.service.WorkingHoursLogService;

@Service
public class WorkingHoursLogServiceImpl implements WorkingHoursLogService {

    @Autowired private WorkingHoursLogRepository workingHoursLogRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private PayrollRaportService payrollRaportService;

    public WorkingHoursLogServiceImpl(WorkingHoursLogRepository workingHoursLogRepository, EmployeeRepository employeeRepository, PayrollRaportService payrollRaportService) {
        this.workingHoursLogRepository = workingHoursLogRepository;
        this.employeeRepository = employeeRepository;
        this.payrollRaportService = payrollRaportService;
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

    @Override
    public WorkingHoursLogDto saveWorkingLogForEmployee(WorkingHoursLogDto workingHoursLogDto) {
        // check if worklog for given month already exists
        if (workingHoursLogRepository.findByDateBetweenAndEmployeeId(LocalDate.of(workingHoursLogDto.getYear(), workingHoursLogDto.getMonth(), 1), LocalDate.of(workingHoursLogDto.getYear(), workingHoursLogDto.getMonth(), 28), workingHoursLogDto.getEmployeeId()).size() != 0) {;
            throw new LastMonthRaportExistsException("Working log already exists!");
        }

        if(workingHoursLogDto.getMonth() == null || workingHoursLogDto.getYear() == null) {
            throw new InvalidDataGivenException("Date was not given!");
        }

        WorkingHoursLog workingHoursLog = new WorkingHoursLog();

        workingHoursLog.setEmployee(employeeRepository.findById(workingHoursLogDto.getEmployeeId()).orElseThrow(() -> new ObjectNotFoundException("Employee could not be found!")));
        workingHoursLog.setHoursWorked(workingHoursLogDto.getHoursWorked());
        workingHoursLog.setPayrollRaport(null);
        workingHoursLog.setDate(LocalDate.of(workingHoursLogDto.getYear(), workingHoursLogDto.getMonth(), 1));

        return mapToWorkingHoursLogDto(workingHoursLogRepository.save(workingHoursLog));
    }

    @Override
    public List<WorkingHoursLogDto> saveWorkingLogsForAll(DateDto date) {
        // get last month
        LocalDate lastMonthDate;
        LocalDate currentMonth = LocalDate.of(date.getYear(), date.getMonth(), 1);
      
        if (date.getMonth() == 1) lastMonthDate = LocalDate.of(date.getYear() - 1, 12, 1);
        else lastMonthDate = LocalDate.of(date.getYear(), date.getMonth() - 1, 1);

        List<WorkingHoursLog> workingHoursLogs = workingHoursLogRepository.findByDateBetween(lastMonthDate, lastMonthDate);
        List<WorkingHoursLogDto> workingHoursLogsDto = new ArrayList<>();

        for (WorkingHoursLog lastMonthLog : workingHoursLogs) {
            // check if worklog for given employee already exist
            if (workingHoursLogRepository.findByDateBetweenAndEmployeeId(currentMonth, currentMonth, lastMonthLog.getEmployee().getId()).size() != 0) {
                continue;
            }

            WorkingHoursLog newWorkingHoursLog = new WorkingHoursLog();

            newWorkingHoursLog.setEmployee(lastMonthLog.getEmployee());
            newWorkingHoursLog.setPayrollRaport(null);
            newWorkingHoursLog.setHoursWorked(lastMonthLog.getHoursWorked());
            newWorkingHoursLog.setDate(currentMonth);

            workingHoursLogRepository.save(newWorkingHoursLog);
            workingHoursLogsDto.add(mapToWorkingHoursLogDto(newWorkingHoursLog));
        }

        return workingHoursLogsDto;
    }
    
    @Override
    public WorkingHoursLogDto updateWorkLog(WorkingHoursLogDto workingHoursLogDto) {
        if (workingHoursLogDto.getEmployeeId() == null) {
            throw new InvalidDataGivenException("Employee ID was not given!");
        }

        if (workingHoursLogDto.getMonth() == null || workingHoursLogDto.getYear() == null) {
            throw new InvalidDataGivenException("Date was not given!");
        }

        WorkingHoursLog workingHoursLog;
        try {
            workingHoursLog = workingHoursLogRepository.findByDateBetweenAndEmployeeId(LocalDate.of(workingHoursLogDto.getYear(), workingHoursLogDto.getMonth(), 1), LocalDate.of(workingHoursLogDto.getYear(), workingHoursLogDto.getMonth(), 1), workingHoursLogDto.getEmployeeId()).get(0);
        } catch (Exception e) {
            throw new ObjectNotFoundException("Could not found last month working log!");
        }

        workingHoursLog.setHoursWorked(workingHoursLogDto.getHoursWorked());

        WorkingHoursLogDto newWorkingLog = mapToWorkingHoursLogDto(workingHoursLogRepository.save(workingHoursLog));
        
        // update payroll raport from given raport if exists
        try {
            payrollRaportService.updatePayrollRaport(new PayrollRaportDto(null, newWorkingLog.getEmployeeId(), workingHoursLog.getDate().getYear(), workingHoursLog.getDate().getMonthValue(), null, null, null, null, null, null, null));
        } catch (Exception e) {}

        return newWorkingLog;
    } 

    @Override
    public void deleteWorkingLog(Long workingLogId) {
        workingHoursLogRepository.delete(workingHoursLogRepository.findById(workingLogId).orElseThrow(() -> new ObjectNotFoundException("Working log could not be found!")));
    }

    private WorkingHoursLogDto mapToWorkingHoursLogDto(WorkingHoursLog workingLog) {
        WorkingHoursLogDto workingLogDto = new WorkingHoursLogDto();

        workingLogDto.setId(workingLog.getId());
        workingLogDto.setEmployeeId(workingLog.getEmployee().getId());
        workingLogDto.setHoursWorked(workingLog.getHoursWorked());
        workingLogDto.setYear(workingLog.getDate().getYear());
        workingLogDto.setMonth(workingLog.getDate().getMonthValue());

        return workingLogDto;
    }

}
