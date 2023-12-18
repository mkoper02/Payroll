package com.mkoper.payroll.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.dto.DateDto;
import com.mkoper.payroll.dto.PayrollRaportDto;
import com.mkoper.payroll.dto.TaxDto;
import com.mkoper.payroll.exceptions.EmployeeNotFoundException;
import com.mkoper.payroll.model.Benefit;
import com.mkoper.payroll.model.PayrollRaport;
import com.mkoper.payroll.model.Tax;
import com.mkoper.payroll.repository.BenefitRepository;
import com.mkoper.payroll.repository.EmployeeRepository;
import com.mkoper.payroll.repository.PayrollRaportRepository;
import com.mkoper.payroll.repository.TaxRepository;
import com.mkoper.payroll.repository.WorkingHoursLogRepository;
import com.mkoper.payroll.service.PayrollRaportService;

@Service
public class PayrollRaportServiceImpl implements PayrollRaportService {
    
    @Autowired private PayrollRaportRepository payrollRaportRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private WorkingHoursLogRepository workingHoursLogRepository;
    @Autowired private TaxRepository taxRepository;
    @Autowired private BenefitRepository benefitRepository;

    public PayrollRaportServiceImpl(PayrollRaportRepository payrollRaportRepository, EmployeeRepository employeeRepository, WorkingHoursLogRepository workingHoursLogRepository, TaxRepository taxRepository, BenefitRepository benefitRepository) {
        this.payrollRaportRepository = payrollRaportRepository;
        this.employeeRepository = employeeRepository;
        this.workingHoursLogRepository = workingHoursLogRepository;
        this.taxRepository = taxRepository;
        this.benefitRepository = benefitRepository;
    }

    @Override
    public List<PayrollRaportDto> getPayrollRaportsByEmployeeId(Long employeeId) {
        List<PayrollRaport> payrollRaports = payrollRaportRepository.findByEmployeeId(employeeId);
        return payrollRaports.stream().map((payrollRaport) -> mapToPayrollRaportDto(payrollRaport)).collect(Collectors.toList());
    }

    @Override
    public List<PayrollRaportDto> getPayrollRaportsByYear(Integer year, Long employeeId) {
        List<PayrollRaport> payrollRaportsBetweenDates = payrollRaportRepository.findByDateBetweenAndEmployeeId(LocalDate.of(year, 01, 01), LocalDate.of(year, 12, 31), employeeId);
        return payrollRaportsBetweenDates.stream().map((payrollRaport) -> mapToPayrollRaportDto(payrollRaport)).collect(Collectors.toList());
    }

    @Override
    public PayrollRaportDto savePayrollRaportForEmployee(PayrollRaportDto payrollRaportDto) {
        // check if payroll raport for given month already exists
        if (payrollRaportRepository.findByDateBetweenAndEmployeeId(LocalDate.of(payrollRaportDto.getYear(), payrollRaportDto.getMonth(), 1), LocalDate.of(payrollRaportDto.getYear(), payrollRaportDto.getMonth(), 28), payrollRaportDto.getEmployeeId()).size() != 0) {;
            throw new IllegalArgumentException("Payroll raport already exists!");
        }

        if (payrollRaportDto.getMonth() == null || payrollRaportDto.getYear() == null) {
            throw new IllegalArgumentException("Date was not given!");
        }

        PayrollRaport payrollRaport = new PayrollRaport();

        payrollRaport.setEmployee(employeeRepository.findById(payrollRaportDto.getEmployeeId()).orElseThrow(() -> new EmployeeNotFoundException("Employee could not be found!")));
        payrollRaport.setSalary(payrollRaport.getEmployee().getSalary());
        payrollRaport.setDate(LocalDate.of(payrollRaportDto.getYear(), payrollRaportDto.getMonth(), 1));
        payrollRaport.setWorkingLog(workingHoursLogRepository.findByDateBetweenAndEmployeeId(payrollRaport.getDate(), payrollRaport.getDate(), payrollRaport.getEmployee().getId()).get(0));

        // calculate bonus 
        Float bonus = 0f;

        // pay extra for overtime
        if (payrollRaport.getWorkingLog().getHoursWorked() > payrollRaport.getSalary().getHours()) {
            bonus += payrollRaport.getSalary().getHourlyWage() * (payrollRaport.getWorkingLog().getHoursWorked() - payrollRaport.getSalary().getHours()) * 1.2f;
        }
        // pay extra every fourth month
        if (payrollRaport.getDate().getMonthValue() % 3 == 0) bonus += 200;

        payrollRaport.setBonus(bonus);
        payrollRaportDto.setBonus(bonus);
        
        // set taxes
        List<Tax> taxes = new ArrayList<>();

        // total amount (always asume that month has 4 weeks)
        Float totalAmount = (payrollRaport.getSalary().getHours() * payrollRaport.getSalary().getHourlyWage() * 4) + bonus;
        payrollRaport.setTotalAmount(totalAmount);
        payrollRaportDto.setTotalAmount(totalAmount);

        // people below age 26 dont pay taxes 
        if (Period.between(payrollRaport.getEmployee().getDateOfBirth(), LocalDate.now()).getYears() <= 26)
            payrollRaport.setPayrollraportTaxes(null);

        // Umowa o prace: zdrowotny, emerytalny, rentowy, chorobowy, dochodowy
        else if (payrollRaport.getSalary().getContractType().equals("Umowa o pracę")) {
            taxes.add(taxRepository.findByName("Zdrowotny").get());
            taxes.add(taxRepository.findByName("Emerytalny").get());
            taxes.add(taxRepository.findByName("Rentowy").get());
            taxes.add(taxRepository.findByName("Chorobowy").get());
            taxes.add(taxRepository.findByName("Dochodowy").get());
        }

        // Umowa-zlecenie: zdrowotny, dochodowy
        else if (payrollRaport.getSalary().getContractType().equals("Umowa-zlecenie")) {
            taxes.add(taxRepository.findByName("Zdrowotny").get());
            taxes.add(taxRepository.findByName("Dochodowy").get());
        }

        payrollRaport.setPayrollraportTaxes(taxes);

        // save taxes
        List<TaxDto> taxesDto = new ArrayList<>();
        payrollRaport.getPayrollraportTaxes().forEach(tax -> {
            TaxDto taxDto = new TaxDto();
        
            taxDto.setName(tax.getName());
            taxDto.setAmount(tax.getCost() * totalAmount);

            taxesDto.add(taxDto);
        });
        payrollRaportDto.setTaxes(taxesDto);

        // set benefits
        List<Benefit> benefits = new ArrayList<>();
        if (!payrollRaportDto.getBenefits().equals(null)) {

            payrollRaportDto.getBenefits().forEach(benefit -> {
                switch (benefit.getName().toLowerCase()) {
                    case "siłownia":
                        benefits.add(benefitRepository.findByName("Siłownia").get());
                        break;
                
                    case "catering":
                        benefits.add(benefitRepository.findByName("Catering").get());
                        break;
    
                    case "basen":
                        benefits.add(benefitRepository.findByName("Basen").get());
                        break;
    
                    case "podstawowy pakiet medyczny":
                        benefits.add(benefitRepository.findByName("Podstawowy pakiet medyczny").get());
                        break;
    
                    case "standardowy pakiet medyczny":
                        benefits.add(benefitRepository.findByName("Standardowy pakiet medyczny").get());
                        break;
    
                    case "rozszerzony pakiet medyczny":
                        benefits.add(benefitRepository.findByName("Rozszerzony pakiet medyczny").get());
                        break;
    
                    case "ubezpieczenie na życie":
                        benefits.add(benefitRepository.findByName("Ubezpieczenie na życie").get());
                        break;
    
                    case "bilet mpk":
                        benefits.add(benefitRepository.findByName("Bilet MPK").get());
                        break;
    
                    default:
                        break;
                }
            });
        }

        payrollRaport.setPayrollraportBenefits(benefits);
        payrollRaportDto.setBenefits(payrollRaport.getPayrollraportBenefits());

        // calculate net salary
        Float netSalary = totalAmount;
        for (TaxDto taxDto : taxesDto) {
            netSalary -= taxDto.getAmount();
        }
        for (Benefit benefit : benefits) {
            netSalary -=benefit.getCost();
        }
        payrollRaport.setNetSalary(netSalary);
        payrollRaportDto.setNetSalary(netSalary);

        payrollRaportRepository.save(payrollRaport);
        payrollRaportDto.setId(payrollRaport.getId());
        return payrollRaportDto;
    }

    @Override
    public List<PayrollRaportDto> savePayrollRaportsForAll(DateDto date) {
        // get last month
        LocalDate lastMonthDate;
        LocalDate currentMonth = LocalDate.of(date.getYear(), date.getMonth(), 1);

        // check if payroll raport for given month already exists
        if (payrollRaportRepository.findByDateBetween(currentMonth, currentMonth).size() != 0) {;
            throw new IllegalArgumentException("Payroll raports already exist!");
        }
        
        if (date.getMonth() == 1) lastMonthDate = LocalDate.of(date.getYear() - 1, 12, 1);
        else lastMonthDate = LocalDate.of(date.getYear(), date.getMonth() - 1, 1);

        List<PayrollRaport> payrollRaports = payrollRaportRepository.findByDateBetween(lastMonthDate, currentMonth);
        List<PayrollRaportDto> payrollRaportDtos = new ArrayList<>();

        for (PayrollRaport lastMonthRaport : payrollRaports) {
            PayrollRaport payrollRaport = new PayrollRaport();

            payrollRaport.setEmployee(lastMonthRaport.getEmployee());
            payrollRaport.setSalary(lastMonthRaport.getSalary());
            payrollRaport.setDate(currentMonth);
            payrollRaport.setWorkingLog(workingHoursLogRepository.findByDateBetweenAndEmployeeId(lastMonthDate, currentMonth, payrollRaport.getEmployee().getId()).get(0));

            List<Benefit> benefits = lastMonthRaport.getPayrollraportBenefits();
            payrollRaport.setPayrollraportBenefits(benefits);
            // payrollRaport.setPayrollraportBenefits(null);

            Float bonus = 0f;
            if (payrollRaport.getWorkingLog().getHoursWorked() > payrollRaport.getSalary().getHours()) {
                bonus += payrollRaport.getSalary().getHourlyWage() * (payrollRaport.getWorkingLog().getHoursWorked() - payrollRaport.getSalary().getHours()) * 1.2f;
            }
            // pay extra every fourth month
            if (payrollRaport.getDate().getMonthValue() % 3 == 0) bonus += 200;

            payrollRaport.setBonus(bonus);

            Float totalAmount = (payrollRaport.getSalary().getHours() * payrollRaport.getSalary().getHourlyWage() * 4) + bonus;
            payrollRaport.setTotalAmount(totalAmount);

            // set taxes    
            List<Tax> taxes = new ArrayList<>();

            // people below age 26 dont pay taxes 
            if (Period.between(payrollRaport.getEmployee().getDateOfBirth(), LocalDate.now()).getYears() <= 26)
                payrollRaport.setPayrollraportTaxes(null);

            // Umowa o prace: zdrowotny, emerytalny, rentowy, chorobowy, dochodowy
            else if (payrollRaport.getSalary().getContractType().equals("Umowa o pracę")) {
                taxes.add(taxRepository.findByName("Zdrowotny").get());
                taxes.add(taxRepository.findByName("Emerytalny").get());
                taxes.add(taxRepository.findByName("Rentowy").get());
                taxes.add(taxRepository.findByName("Chorobowy").get());
                taxes.add(taxRepository.findByName("Dochodowy").get());
            }

            // Umowa-zlecenie: zdrowotny, dochodowy
            else if (payrollRaport.getSalary().getContractType().equals("Umowa-zlecenie")) {
                taxes.add(taxRepository.findByName("Zdrowotny").get());
                taxes.add(taxRepository.findByName("Dochodowy").get());
            }

            payrollRaport.setPayrollraportTaxes(taxes);

            List<TaxDto> taxesDto = new ArrayList<>();
            payrollRaport.getPayrollraportTaxes().forEach(tax -> {
                TaxDto taxDto = new TaxDto();
            
                taxDto.setName(tax.getName());
                taxDto.setAmount(tax.getCost() * totalAmount);

                taxesDto.add(taxDto);
            });

            // calculate net salary
            Float netSalary = totalAmount;
            for (TaxDto taxDto : taxesDto) {
                netSalary -= taxDto.getAmount();
            }
            for (Benefit benefit : payrollRaport.getPayrollraportBenefits()) {
                netSalary -=benefit.getCost();
            }
            payrollRaport.setNetSalary(netSalary);

            // payrollRaportRepository.save(payrollRaport);
            payrollRaportDtos.add(mapToPayrollRaportDto(payrollRaport));

        }

        return payrollRaportDtos;
    }

    public PayrollRaportDto updatePayrollRaport(PayrollRaportDto payrollRaportDto) {
        if (payrollRaportDto.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee ID was not given!");
        }

        if (payrollRaportDto.getMonth() == null || payrollRaportDto.getYear() == null) {
            throw new IllegalArgumentException("Date was not given!");
        }

        PayrollRaport payrollRaport = payrollRaportRepository.findByDateBetweenAndEmployeeId(LocalDate.of(payrollRaportDto.getYear(), payrollRaportDto.getMonth(), 1), LocalDate.of(payrollRaportDto.getYear(), payrollRaportDto.getMonth(), 1), payrollRaportDto.getEmployeeId()).get(0);

        if (payrollRaportDto.getBonus() != null) {
            payrollRaport.setBonus(payrollRaportDto.getBonus());
            payrollRaport.setTotalAmount(payrollRaport.getTotalAmount() + payrollRaport.getBonus());
        }

        // set benefits
        List<Benefit> benefits = new ArrayList<>();
        if (payrollRaportDto.getBenefits() != null) {

            payrollRaportDto.getBenefits().forEach(benefit -> {
                switch (benefit.getName().toLowerCase()) {
                    case "siłownia":
                        benefits.add(benefitRepository.findByName("Siłownia").get());
                        break;
                
                    case "catering":
                        benefits.add(benefitRepository.findByName("Catering").get());
                        break;
    
                    case "basen":
                        benefits.add(benefitRepository.findByName("Basen").get());
                        break;
    
                    case "podstawowy pakiet medyczny":
                        benefits.add(benefitRepository.findByName("Podstawowy pakiet medyczny").get());
                        break;
    
                    case "standardowy pakiet medyczny":
                        benefits.add(benefitRepository.findByName("Standardowy pakiet medyczny").get());
                        break;
    
                    case "rozszerzony pakiet medyczny":
                        benefits.add(benefitRepository.findByName("Rozszerzony pakiet medyczny").get());
                        break;
    
                    case "ubezpieczenie na życie":
                        benefits.add(benefitRepository.findByName("Ubezpieczenie na życie").get());
                        break;
    
                    case "bilet mpk":
                        benefits.add(benefitRepository.findByName("Bilet MPK").get());
                        break;
    
                    default:
                        break;
                }
            });
        }
        
        payrollRaport.setPayrollraportBenefits(benefits);
        // TODO: delete relation with benefits

        // save taxes
        List<TaxDto> taxesDto = new ArrayList<>();
        payrollRaport.getPayrollraportTaxes().forEach(tax -> {
            TaxDto taxDto = new TaxDto();
        
            taxDto.setName(tax.getName());
            taxDto.setAmount(tax.getCost() * payrollRaport.getTotalAmount());

            taxesDto.add(taxDto);
        });
        payrollRaportDto.setTaxes(taxesDto);

        // calculate net salary
        Float netSalary = payrollRaport.getTotalAmount();
        for (TaxDto taxDto : taxesDto) {
            netSalary -= taxDto.getAmount();
        }
        for (Benefit benefit : payrollRaport.getPayrollraportBenefits()) {
            netSalary -=benefit.getCost();
        }
        payrollRaport.setNetSalary(netSalary);


        return mapToPayrollRaportDto(payrollRaport);
    }

    private PayrollRaportDto mapToPayrollRaportDto(PayrollRaport payrollRaport) {
        PayrollRaportDto payrollRaportDto = new PayrollRaportDto();

        payrollRaportDto.setId(payrollRaport.getId());
        payrollRaportDto.setEmployeeId(payrollRaport.getEmployee().getId());
        payrollRaportDto.setYear(payrollRaport.getDate().getYear());
        payrollRaportDto.setMonth(payrollRaport.getDate().getMonthValue());
        payrollRaportDto.setBonus(payrollRaport.getBonus());
        payrollRaportDto.setNetSalary(payrollRaport.getNetSalary());
        payrollRaportDto.setBonus(payrollRaport.getBonus());
        payrollRaportDto.setBenefits(payrollRaport.getPayrollraportBenefits());
        payrollRaportDto.setTotalAmount(payrollRaport.getTotalAmount());

        List<TaxDto> taxesDto = new ArrayList<>();
        payrollRaport.getPayrollraportTaxes().forEach(tax -> {
            TaxDto taxDto = new TaxDto();
        
            taxDto.setName(tax.getName());
            taxDto.setAmount(tax.getCost() * payrollRaportDto.getTotalAmount());

            taxesDto.add(taxDto);
        });
        payrollRaportDto.setTaxes(taxesDto);

        return payrollRaportDto;
    }
}
