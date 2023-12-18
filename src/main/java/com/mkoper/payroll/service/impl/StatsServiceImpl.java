package com.mkoper.payroll.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.dto.DateDto;
import com.mkoper.payroll.dto.StatsDto;
import com.mkoper.payroll.model.PayrollRaport;
import com.mkoper.payroll.repository.PayrollRaportRepository;
import com.mkoper.payroll.service.StatsService;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired private PayrollRaportRepository payrollRaportRepository;

    public StatsServiceImpl(PayrollRaportRepository payrollRaportRepository) {
        this.payrollRaportRepository = payrollRaportRepository;
    }

    public List<StatsDto> getStats(DateDto dateDto) {
        if (dateDto.getYear() == null) {
            throw new IllegalArgumentException("Date was not given!");
        }

        List<PayrollRaport> payrollRaports = new ArrayList<>();
        // get stats for given year
        if (dateDto.getMonth() == null) {
            payrollRaports = payrollRaportRepository.findByDateBetween(LocalDate.of(dateDto.getYear(), 4, 1), LocalDate.of(dateDto.getYear(), 12, 31));;
        }

        // get stats for given month
        else {
            payrollRaports = payrollRaportRepository.findByDateBetween(LocalDate.of(dateDto.getYear(), dateDto.getMonth(), 1), LocalDate.of(dateDto.getYear(), dateDto.getMonth(), 1));
        }

        List<StatsDto> statsDtos = new ArrayList<>();

        statsDtos.add(getSumGrossSalary(payrollRaports));
        statsDtos.add(getSumNetSalary(payrollRaports));
        statsDtos.add(getSumBonuses(payrollRaports));
        statsDtos.add(getAverageGrossSalary(payrollRaports));
        statsDtos.add(getAverageNetSalary(payrollRaports));
        statsDtos.add(getAverageBonus(payrollRaports));

        return statsDtos;
    }

    // return stat with sum of gross salaries from given month
    private StatsDto getSumGrossSalary(List<PayrollRaport> payrollRaports) {
        StatsDto statsDto = new StatsDto();
        statsDto.setName("Sum of gross salaries");

        Float sum = 0f;
        for(PayrollRaport payrollRaport : payrollRaports) {
            sum += payrollRaport.getTotalAmount();
        }

        statsDto.setAmount(sum);
        return statsDto;
    }

    // return stat with sum of net salaries from given month
    private StatsDto getSumNetSalary(List<PayrollRaport> payrollRaports) {
        StatsDto statsDto = new StatsDto();
        statsDto.setName("Sum of net salaries");

        Float sum = 0f;
        for(PayrollRaport payrollRaport : payrollRaports) {
            sum += payrollRaport.getNetSalary();
        }

        statsDto.setAmount(sum);
        return statsDto;
    }

    // return stat with sum of bonuses form given month
    private StatsDto getSumBonuses (List<PayrollRaport> payrollRaports) {
        StatsDto statsDto = new StatsDto();
        statsDto.setName("Sum of bonuses");

        Float sum = 0f;
        for(PayrollRaport payrollRaport : payrollRaports) {
            sum += payrollRaport.getBonus();
        }
        
        statsDto.setAmount(sum);
        return statsDto;
    }

    // return stat with average gross salary for given month
    private StatsDto getAverageGrossSalary(List<PayrollRaport> payrollRaports) {
        StatsDto statsDto = new StatsDto();
        statsDto.setName("Average gross salary");

        Float sum = 0f;
        for(PayrollRaport payrollRaport : payrollRaports) {
            sum += payrollRaport.getTotalAmount();
        }
        
        if (payrollRaports.size() != 0) statsDto.setAmount(sum / payrollRaports.size());
        else statsDto.setAmount(0f);

        return statsDto;
    }

    // return stat with average net salary for given month
    private StatsDto getAverageNetSalary(List<PayrollRaport> payrollRaports) {
        StatsDto statsDto = new StatsDto();
        statsDto.setName("Average net salary");

        Float sum = 0f;
        for(PayrollRaport payrollRaport : payrollRaports) {
            sum += payrollRaport.getNetSalary();
        }
        
        if (payrollRaports.size() != 0) statsDto.setAmount(sum / payrollRaports.size());
        else statsDto.setAmount(0f);

        return statsDto;
    }

    // return stat with average bonus for given month
    private StatsDto getAverageBonus(List<PayrollRaport> payrollRaports) {
        StatsDto statsDto = new StatsDto();
        statsDto.setName("Average bonus");

        Float sum = 0f;
        for(PayrollRaport payrollRaport : payrollRaports) {
            sum += payrollRaport.getBonus();
        }
        
        if (payrollRaports.size() != 0) statsDto.setAmount(sum / payrollRaports.size());
        else statsDto.setAmount(0f);

        return statsDto;
    }
}
