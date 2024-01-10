package com.mkoper.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mkoper.payroll.dto.TaxDto;
import com.mkoper.payroll.model.Tax;
import com.mkoper.payroll.service.TaxService;

@RestController
@CrossOrigin
public class TaxController {
    
    @Autowired
    private TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @GetMapping("tax")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    public List<Tax> getTaxes() {
        return taxService.getAll();
    }

    // get tax with given id
    @GetMapping("tax/{taxId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    public ResponseEntity<TaxDto> getTaxId(@PathVariable Long taxId) {
        return ResponseEntity.ok(taxService.getTaxById(taxId));
    }

    @PutMapping("tax/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tax> updateTax(@RequestBody Tax tax) {
        return new ResponseEntity<>(taxService.updateTax(tax), HttpStatus.OK);
    }

    @PostMapping("tax/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tax> addTax(@RequestBody Tax tax) {
        return new ResponseEntity<>(taxService.saveTax(tax), HttpStatus.CREATED);
    }

    @DeleteMapping("tax/delete/{taxId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTax(@PathVariable Long taxId) {
        taxService.deleteTax(taxId);
        return new ResponseEntity<>("Tax deleted", HttpStatus.OK);
    }
}
