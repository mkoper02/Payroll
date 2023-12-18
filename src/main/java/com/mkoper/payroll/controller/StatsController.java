package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.dto.DateDto;
import com.mkoper.payroll.dto.StatsDto;
import com.mkoper.payroll.service.StatsService;

@RestController
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
    @GetMapping("stats")
    public List<StatsDto> getStatsForGivenMonth(@RequestBody DateDto dateDto) {
        return statsService.getStats(dateDto);
    }
}
