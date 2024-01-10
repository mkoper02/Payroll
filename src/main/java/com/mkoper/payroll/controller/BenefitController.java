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

import com.mkoper.payroll.dto.BenefitDto;
import com.mkoper.payroll.model.Benefit;
import com.mkoper.payroll.service.BenefitService;

@RestController
@CrossOrigin
public class BenefitController {
    
    @Autowired
    private BenefitService benefitService;

    public BenefitController(BenefitService benefitService) {
        this.benefitService = benefitService;
    }

    // get all benefits
    @GetMapping("benefit")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    public List<Benefit> getBenefits() {
        return benefitService.getAll();
    }

    // get benefit with given id
    @GetMapping("benefit/{benefitId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    public ResponseEntity<BenefitDto> getBenefitId(@PathVariable Long benefitId) {
        return ResponseEntity.ok(benefitService.getBenefitById(benefitId));
    }

    // update benefit with given ID
    @PutMapping("benefit/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Benefit> updateBenefit(@RequestBody Benefit benefit) {
        return new ResponseEntity<>(benefitService.updateBenefit(benefit), HttpStatus.OK);
    }

    // create new benefit
    @PostMapping("benefit/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Benefit> addBenefit(@RequestBody Benefit benefit) {
        return new ResponseEntity<>(benefitService.saveBenefit(benefit), HttpStatus.CREATED);
    }

    // delete benefit with given ID
    @DeleteMapping("benefit/delete/{benefitId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBenefit(@PathVariable Long benefitId) {
        benefitService.deleteBenefit(benefitId);
        return new ResponseEntity<>("Benefit deleted", HttpStatus.OK);
    } 
}
