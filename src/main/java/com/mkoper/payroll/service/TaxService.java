package com.mkoper.payroll.service;

import java.util.List;

import com.mkoper.payroll.model.Tax;

public interface TaxService {
    // Return all taxes in db
    public List<Tax> getAll();

    // Save tax to the db
    public Tax saveTax(Tax tax);

    // Update tax with given ID
    public Tax updateTax(Tax tax);

    // delete Tax with given ID
    public void deleteTax(Long taxId);
}
