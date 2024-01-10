package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.dto.DateDto;
import com.mkoper.payroll.dto.WorkingHoursLogDto;
import com.mkoper.payroll.service.WorkingHoursLogService;

@RestController
@CrossOrigin
public class WorkingHoursLogController {
    
    @Autowired
    private WorkingHoursLogService workingHoursLogService;

    public WorkingHoursLogController(WorkingHoursLogService workingHoursLogService) {
        this.workingHoursLogService = workingHoursLogService;
    }

    @GetMapping("employee/{employeeId}/workinglogs")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    public List<WorkingHoursLogDto> getWorkingLogsByEmployeeId(@PathVariable Long employeeId) {
        return workingHoursLogService.getWorkingLogsByEmployeeId(employeeId);
    }

    @GetMapping("employee/{employeeId}/workinglogs/{year}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    public List<WorkingHoursLogDto> getWorkingLogsByYear(@PathVariable Long employeeId, @PathVariable Integer year) {
        return workingHoursLogService.getWorkingLogByYear(employeeId, year);
    }

    @PutMapping("workinglogs/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<WorkingHoursLogDto> updateWorkLog(@RequestBody WorkingHoursLogDto workingHoursLogDto) {
        return new ResponseEntity<>(workingHoursLogService.updateWorkLog(workingHoursLogDto), HttpStatus.OK);
    }

    // create working log for employee with given ID
    @PostMapping("workinglogs/create/employee")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<WorkingHoursLogDto> addWorkingLogForEmployee(@RequestBody WorkingHoursLogDto workingHoursLogDto) {
        return new ResponseEntity<>(workingHoursLogService.saveWorkingLogForEmployee(workingHoursLogDto), HttpStatus.CREATED);
    }

    // create working logs for all employee (data will be the same as the last month)
    @PostMapping("workinglogs/create/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<List<WorkingHoursLogDto>> addWorkingLogsForAll(@RequestBody DateDto dateDto) {
        return new ResponseEntity<>(workingHoursLogService.saveWorkingLogsForAll(dateDto), HttpStatus.CREATED);
    }

    @DeleteMapping("workinglogs/delete/{workingLogId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteWorkLog(@PathVariable Long workingLogId) {
        workingHoursLogService.deleteWorkingLog(workingLogId);
        return new ResponseEntity<>("Working log deleted", HttpStatus.OK);
    }
}
