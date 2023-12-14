package com.mkoper.payroll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mkoper.payroll.model.Tax;
import com.mkoper.payroll.repository.TaxRepository;
import com.mkoper.payroll.service.TaxService;

public class TaxServiceImpl implements TaxService {

    @Autowired
    private TaxRepository taxRepository;

    public TaxServiceImpl(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    @Override
    public List<Tax> getAll() {
        return taxRepository.findAll();
    }

    @Override
    public Tax updateTax(Tax tax, Long taxId) {
        tax.setId(taxId);
        return taxRepository.save(tax);
    }

    @Override
    public Tax saveTax(Tax tax) {
        return taxRepository.save(tax);
    }

    @Override
    public void deleteTax(Long taxId) {
        Tax tax = taxRepository.findById(taxId).get();
        taxRepository.delete(tax);
    }
}
