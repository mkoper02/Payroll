package com.mkoper.payroll.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class PayrollRaport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(nullable = true)
    private Float bonus;

    @Column(name = "total_amount", nullable = false)
    private Float totalAmount;

    @Column(name = "net_salary", nullable = false)
    private Float netSalary;

    // FOREIGN KEYS
    // relation with employee table
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    // relation with working_hours_log table
    @OneToOne
    @JoinColumn(name = "working_hours_log_id")
    private WorkingHoursLog workingLog;

    // relation with salary table
    @ManyToOne
    @JoinColumn(name = "salary_id")
    private Salary salary;

    // relation with benefit table
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
        name = "payroll_raport_benefit",
        joinColumns = @JoinColumn(name = "payrollraport_id"),
        inverseJoinColumns = @JoinColumn(name = "benefit_id")
    )
    private List<Benefit> payrollraportBenefits;

    // relation with tax table
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
        name = "payroll_raport_tax",
        joinColumns = @JoinColumn(name = "payrollraport_id"),
        inverseJoinColumns = @JoinColumn(name = "tax_id")
    )
    private List<Tax> payrollraportTaxes;
    
    public PayrollRaport() {}

    public PayrollRaport(Long id, LocalDate date, Float bonus, Float totalAmount, Float netSalary, Employee employee, WorkingHoursLog workingLog, Salary salary) {
        this.id = id;
        this.date = date;
        this.bonus = bonus;
        this.totalAmount = totalAmount;
        this.netSalary = netSalary;
        this.employee = employee;
        this.workingLog = workingLog;
        this.salary = salary;
    }

    public PayrollRaport(LocalDate date, Float bonus, Float totalAmount, Float netSalary, Employee employee, WorkingHoursLog workingLog, Salary salary) {
        this.date = date;
        this.bonus = bonus;
        this.totalAmount = totalAmount;
        this.netSalary = netSalary;
        this.employee = employee;
        this.workingLog = workingLog;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getBonus() {
        return bonus;
    }

    public void setBonus(Float bonus) {
        this.bonus = bonus;
    }

    public Float getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(Float netSalary) {
        this.netSalary = netSalary;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public WorkingHoursLog getWorkingLog() {
        return workingLog;
    }

    public void setWorkingLog(WorkingHoursLog workingLog) {
        this.workingLog = workingLog;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public List<Benefit> getPayrollraportBenefits() {
        return payrollraportBenefits;
    }

    public void setPayrollraportBenefits(List<Benefit> payrollraportBenefits) {
        this.payrollraportBenefits = payrollraportBenefits;
    }

    public List<Tax> getPayrollraportTaxes() {
        return payrollraportTaxes;
    }

    public void setPayrollraportTaxes(List<Tax> payrollraportTaxes) {
        this.payrollraportTaxes = payrollraportTaxes;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
