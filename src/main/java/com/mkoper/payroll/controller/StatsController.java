package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.dto.DateDto;
import com.mkoper.payroll.dto.StatsDto;
import com.mkoper.payroll.service.StatsService;

@RestController
@CrossOrigin
public class StatsController {

    @Autowired private StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    // return stats such as:
    // - sum of all gross salaries
    // - sum of all net salaries
    // - sum of all bonuses
    // - average gross salary
    // - average net salary
    // - average of bonuses
    @GetMapping("stats/{year}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public List<StatsDto> getStatsForGivenYear(@PathVariable("year") Integer year) {
        return statsService.getStats(new DateDto(year, null));
    }

    @GetMapping("stats/{year}/{month}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public List<StatsDto> getStatsForGivenMonth(@PathVariable("year") Integer year, @PathVariable("month") Integer month) {
        return statsService.getStats(new DateDto(year, month));
    }
}
