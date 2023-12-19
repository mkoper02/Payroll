package com.mkoper.payroll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.exceptions.InvalidDataGivenException;
import com.mkoper.payroll.exceptions.ObjectNotFoundException;
import com.mkoper.payroll.model.Tax;
import com.mkoper.payroll.repository.TaxRepository;
import com.mkoper.payroll.service.TaxService;

@Service
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
    public Tax updateTax(Tax tax) {
        if (tax.getId() == null) {
            throw new InvalidDataGivenException("ID was not given!");
        }

        Tax taxDb = taxRepository.findById(tax.getId()).orElseThrow(() -> new ObjectNotFoundException("Tax with given ID could not be found!"));
        
        if(tax.getName() != null) taxDb.setName(tax.getName());
        if(tax.getCost() != null) taxDb.setCost(tax.getCost());

        return taxRepository.save(taxDb);
    }

    @Override
    public Tax saveTax(Tax tax) {
        return taxRepository.save(tax);
    }

    @Override
    public void deleteTax(Long taxId) {
        Tax tax = taxRepository.findById(taxId).orElseThrow(() -> new ObjectNotFoundException("Tax with given ID could not be found!"));
        taxRepository.delete(tax);
    }
}
