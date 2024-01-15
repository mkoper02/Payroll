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
import com.mkoper.payroll.exceptions.InvalidDataGivenException;
import com.mkoper.payroll.exceptions.LastMonthRaportExistsException;
import com.mkoper.payroll.exceptions.ObjectNotFoundException;
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
            throw new LastMonthRaportExistsException("Payroll raport already exists!");
        }

        if (payrollRaportDto.getMonth() == null || payrollRaportDto.getYear() == null) {
            throw new InvalidDataGivenException("Date was not given!");
        }

        PayrollRaport payrollRaport = new PayrollRaport();

        payrollRaport.setEmployee(employeeRepository.findById(payrollRaportDto.getEmployeeId()).orElseThrow(() -> new ObjectNotFoundException("Employee could not be found!")));
        payrollRaport.setSalary(payrollRaport.getEmployee().getSalary());
        payrollRaport.setDate(LocalDate.of(payrollRaportDto.getYear(), payrollRaportDto.getMonth(), 1));

        try {
            payrollRaport.setWorkingLog(workingHoursLogRepository.findByDateBetweenAndEmployeeId(payrollRaport.getDate(), payrollRaport.getDate(), payrollRaport.getEmployee().getId()).get(0));
        } catch (Exception e) {
            throw new ObjectNotFoundException("Could not find working log for employee!");
        }

        payrollRaport.setBonus(calculateBonus(payrollRaport));
        payrollRaport.setTotalAmount(calculateTotalAmount(payrollRaport));
        payrollRaport.setPayrollraportTaxes(creareTaxList(payrollRaport));
        payrollRaport.setPayrollraportBenefits(createBenefitList(payrollRaportDto));
        payrollRaport.setNetSalary(calculateNetSalary(payrollRaport));

        payrollRaportRepository.save(payrollRaport);
        return mapToPayrollRaportDto(payrollRaport);
    }

    @Override
    public List<PayrollRaportDto> savePayrollRaportsForAll(DateDto date) {
        // get last month
        LocalDate lastMonthDate;
        LocalDate currentMonth = LocalDate.of(date.getYear(), date.getMonth(), 1);
        
        if (date.getMonth() == 1) lastMonthDate = LocalDate.of(date.getYear() - 1, 12, 1);
        else lastMonthDate = LocalDate.of(date.getYear(), date.getMonth() - 1, 1);

        List<PayrollRaportDto> payrollRaports = new ArrayList<>();
        
        for (PayrollRaport lastMonthRaport :  payrollRaportRepository.findByDateBetween(lastMonthDate, currentMonth)) {
            // check if payroll raport for given employee already exist
            if (payrollRaportRepository.findByDateBetweenAndEmployeeId(currentMonth, currentMonth, lastMonthRaport.getEmployee().getId()).size() != 0) {
                continue;
            }

            PayrollRaport payrollRaport = new PayrollRaport();
            
            payrollRaport.setEmployee(lastMonthRaport.getEmployee());
            payrollRaport.setSalary(lastMonthRaport.getSalary());
            payrollRaport.setDate(currentMonth);

            try {
                payrollRaport.setWorkingLog(workingHoursLogRepository.findByDateBetweenAndEmployeeId(currentMonth, currentMonth, payrollRaport.getEmployee().getId()).get(0));
            } catch (Exception e) {
                throw new ObjectNotFoundException("Working log for employee " + payrollRaport.getEmployee().getId() + " could not be found!");
            }
            
            payrollRaport.setBonus(calculateBonus(payrollRaport));
            payrollRaport.setTotalAmount(calculateTotalAmount(payrollRaport));
            
            payrollRaport.setPayrollraportBenefits(createBenefitList(mapToPayrollRaportDto(lastMonthRaport)));
            payrollRaport.setPayrollraportTaxes(creareTaxList(payrollRaport));

            payrollRaport.setNetSalary(calculateNetSalary(payrollRaport));

            payrollRaportRepository.save(payrollRaport);
            payrollRaports.add(mapToPayrollRaportDto(payrollRaport));
        }

        return payrollRaports;
    }

    public PayrollRaportDto updatePayrollRaport(PayrollRaportDto payrollRaportDto) {
        if (payrollRaportDto.getEmployeeId() == null) {
            throw new InvalidDataGivenException("Employee ID was not given!");
        }

        if (payrollRaportDto.getMonth() == null || payrollRaportDto.getYear() == null) {
            throw new InvalidDataGivenException("Date was not given!");
        }
        
        // get payroll raport
        // check if working log for given payroll raport exists
        PayrollRaport oldPayrollRaport = new PayrollRaport();
        try {
            oldPayrollRaport = payrollRaportRepository.findByDateBetweenAndEmployeeId(LocalDate.of(payrollRaportDto.getYear(), payrollRaportDto.getMonth(), 1), LocalDate.of(payrollRaportDto.getYear(), payrollRaportDto.getMonth(), 1), payrollRaportDto.getEmployeeId()).get(0);
        } catch (Exception e) {
            throw new ObjectNotFoundException("Payroll raport was not found!");
        }

        PayrollRaport newPayrollRaport = new PayrollRaport();

        newPayrollRaport.setEmployee(oldPayrollRaport.getEmployee());
        newPayrollRaport.setDate(oldPayrollRaport.getDate());
        newPayrollRaport.setSalary(oldPayrollRaport.getSalary());
        newPayrollRaport.setWorkingLog(oldPayrollRaport.getWorkingLog());

        // update bonus
        if (payrollRaportDto.getBonus() != null) {
            newPayrollRaport.setBonus(payrollRaportDto.getBonus());
            newPayrollRaport.setTotalAmount(calculateTotalAmount(newPayrollRaport));
        }

        else {
            newPayrollRaport.setBonus(oldPayrollRaport.getBonus());
            newPayrollRaport.setTotalAmount(calculateTotalAmount(newPayrollRaport));
        }

        if (payrollRaportDto.getBenefits() != null) 
            newPayrollRaport.setPayrollraportBenefits(createBenefitList(payrollRaportDto));
        
        else 
            newPayrollRaport.setPayrollraportBenefits(oldPayrollRaport.getPayrollraportBenefits());

        newPayrollRaport.setPayrollraportTaxes(creareTaxList(newPayrollRaport));

        newPayrollRaport.setNetSalary(calculateNetSalary(newPayrollRaport));

        payrollRaportRepository.deleteById(oldPayrollRaport.getId());
        payrollRaportRepository.save(newPayrollRaport);

        return mapToPayrollRaportDto(newPayrollRaport);
    }

    public void deleteByEmployeeIdDate(DateDto dateDto, Long employeeId) {
        PayrollRaport payrollRaport = new PayrollRaport();

        try {
            payrollRaport = payrollRaportRepository.findByDateBetweenAndEmployeeId(LocalDate.of(dateDto.getYear(), dateDto.getMonth(), 1), LocalDate.of(dateDto.getYear(), dateDto.getMonth(), 1), employeeId).get(0);
        } catch (Exception e) {
            throw new ObjectNotFoundException("Payroll raport could not be found!");
        }

        payrollRaportRepository.delete(payrollRaport);
    }

    public void deleteByEmployeeId(Long employeeId) {
        List<PayrollRaport> payrollRaports = payrollRaportRepository.findByEmployeeId(employeeId);

        for (PayrollRaport payrollRaport : payrollRaports) {
            payrollRaportRepository.delete(payrollRaport);
        }
    }

    private PayrollRaportDto mapToPayrollRaportDto(PayrollRaport payrollRaport) {
        PayrollRaportDto payrollRaportDto = new PayrollRaportDto();

        payrollRaportDto.setId(payrollRaport.getId());
        payrollRaportDto.setEmployeeId(payrollRaport.getEmployee().getId());
        payrollRaportDto.setYear(payrollRaport.getDate().getYear());
        payrollRaportDto.setMonth(payrollRaport.getDate().getMonthValue());
        payrollRaportDto.setHoursWorked(payrollRaport.getWorkingLog().getHoursWorked());
        payrollRaportDto.setWage(payrollRaport.getSalary().getHourlyWage());
        payrollRaportDto.setBonus(payrollRaport.getBonus());
        payrollRaportDto.setNetSalary(payrollRaport.getNetSalary());
        payrollRaportDto.setBonus(payrollRaport.getBonus());
        payrollRaportDto.setBenefits(payrollRaport.getPayrollraportBenefits());
        payrollRaportDto.setTotalAmount(payrollRaport.getTotalAmount());
        payrollRaportDto.setTaxes(calculateTaxesCost(payrollRaport));

        return payrollRaportDto;
    }

    // return list of taxDto - amount due to pay for idividual tax
    private List<TaxDto> calculateTaxesCost(PayrollRaport payrollRaport) {
        List<TaxDto> taxDtoList = new ArrayList<>();

        payrollRaport.getPayrollraportTaxes().forEach(tax -> {
            TaxDto taxDto = new TaxDto();

            taxDto.setName(tax.getName());
            taxDto.setAmount(tax.getCost() * payrollRaport.getTotalAmount());

            taxDtoList.add(taxDto);
        });

        return taxDtoList;
    }

    // create list of taxes that given employee has to pay
    private List<Tax> creareTaxList(PayrollRaport payrollRaport) {
        List<Tax> taxList = new ArrayList<>();

        // people below age 26 dont pay taxes 
        if (Period.between(payrollRaport.getEmployee().getDateOfBirth(), LocalDate.now()).getYears() <= 26)
            return taxList;

        // Umowa o prace: zdrowotny, emerytalny, rentowy, chorobowy, dochodowy
        else if (payrollRaport.getSalary().getContractType().equals("Umowa o prace")) {
            taxList.add(taxRepository.findByName("Zdrowotny").get());
            taxList.add(taxRepository.findByName("Emerytalny").get());
            taxList.add(taxRepository.findByName("Rentowy").get());
            taxList.add(taxRepository.findByName("Chorobowy").get());
            taxList.add(taxRepository.findByName("Dochodowy").get());
        }

        // Umowa-zlecenie: zdrowotny, dochodowy
        else if (payrollRaport.getSalary().getContractType().equals("Umowa-zlecenie")) {
            taxList.add(taxRepository.findByName("Zdrowotny").get());
            taxList.add(taxRepository.findByName("Dochodowy").get());
        }

        return taxList;
    }

    // create list of benefits that given employee has chosen
    private List<Benefit> createBenefitList(PayrollRaportDto payrollRaportDto) {
        List<Benefit> benefitList = new ArrayList<>();
        
        if (!payrollRaportDto.getBenefits().equals(null)) {

            payrollRaportDto.getBenefits().forEach(benefit -> {
                switch (benefit.getName().toLowerCase()) {
                    case "siłownia":
                        benefitList.add(benefitRepository.findByName("Siłownia").get());
                        break;
                
                    case "catering":
                        benefitList.add(benefitRepository.findByName("Catering").get());
                        break;
    
                    case "basen":
                        benefitList.add(benefitRepository.findByName("Basen").get());
                        break;
    
                    case "podstawowy pakiet medyczny":
                        benefitList.add(benefitRepository.findByName("Podstawowy pakiet medyczny").get());
                        break;
    
                    case "standardowy pakiet medyczny":
                        benefitList.add(benefitRepository.findByName("Standardowy pakiet medyczny").get());
                        break;
    
                    case "rozszerzony pakiet medyczny":
                        benefitList.add(benefitRepository.findByName("Rozszerzony pakiet medyczny").get());
                        break;
    
                    case "ubezpieczenie na życie":
                        benefitList.add(benefitRepository.findByName("Ubezpieczenie na życie").get());
                        break;
    
                    case "bilet mpk":
                        benefitList.add(benefitRepository.findByName("Bilet MPK").get());
                        break;
    
                    default:
                        break;
                }
            });
        }


        return benefitList;
    }

    // calculate bonus for given payroll raport
    private Float calculateBonus(PayrollRaport payrollRaport) {
        Float bonus = 0f;

        // pay extra for overtime
        if (payrollRaport.getWorkingLog().getHoursWorked() > payrollRaport.getSalary().getHours()) {
            bonus += payrollRaport.getSalary().getHourlyWage() * (payrollRaport.getWorkingLog().getHoursWorked() - payrollRaport.getSalary().getHours()) * 1.2f;
        }

        // pay extra every third month
        if (payrollRaport.getDate().getMonthValue() % 3 == 0) bonus += 200;

        return bonus;
    }

    // calculate total amount for given payroll raport
    private Float calculateTotalAmount(PayrollRaport payrollRaport) {
        Float totalAmount = 0f;

        // check if employee is paid hourly or for full month
        if (payrollRaport.getSalary().getHours() != null) {
            // total amount (always asume that month has 4 weeks)
            totalAmount = (payrollRaport.getWorkingLog().getHoursWorked() * payrollRaport.getSalary().getHourlyWage()) + payrollRaport.getBonus();
        }

        else {
            totalAmount = payrollRaport.getSalary().getHourlyWage() + payrollRaport.getBonus();
        }

        return totalAmount;
    }

    // calculate net salary for given payroll raport
    private Float calculateNetSalary(PayrollRaport payrollRaport) {
        Float netSalary = payrollRaport.getTotalAmount();

        // taxes
        for (TaxDto taxDto : calculateTaxesCost(payrollRaport)) {
            netSalary -= taxDto.getAmount();
        }

        // benefits
        for (Benefit benefit : payrollRaport.getPayrollraportBenefits()) {
            netSalary -= benefit.getCost();
        }

        return netSalary;
    }
}
