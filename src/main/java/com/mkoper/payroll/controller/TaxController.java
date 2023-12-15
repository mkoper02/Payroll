package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.model.Tax;
import com.mkoper.payroll.service.TaxService;

@RestController
public class TaxController {
    
    @Autowired
    private TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @GetMapping("tax")
    public List<Tax> getTaxes() {
        return taxService.getAll();
    }

    @PutMapping("tax/update")
    public ResponseEntity<Tax> updateTax(@RequestBody Tax tax) {
        return new ResponseEntity<>(taxService.updateTax(tax), HttpStatus.OK);
    }

    @PostMapping("tax/create")
    public ResponseEntity<Tax> addTax(@RequestBody Tax tax) {
        return new ResponseEntity<>(taxService.saveTax(tax), HttpStatus.OK);
    }

    @DeleteMapping("tax/delete/{taxId}")
    public ResponseEntity<String> deleteTax(@PathVariable Long taxId) {
        taxService.deleteTax(taxId);
        return new ResponseEntity<>("Tax deleted", HttpStatus.OK);
    }
}
