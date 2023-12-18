package com.mkoper.payroll.service;

import java.util.List;

import com.mkoper.payroll.dto.DateDto;
import com.mkoper.payroll.dto.StatsDto;
public interface StatsService {
    // return stats for given date
    public List<StatsDto> getStats(DateDto dateDto);
}
