package com.mkoper.payroll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkoper.payroll.exceptions.InvalidDataGivenException;
import com.mkoper.payroll.exceptions.ObjectNotFoundException;
import com.mkoper.payroll.model.Benefit;
import com.mkoper.payroll.repository.BenefitRepository;
import com.mkoper.payroll.service.BenefitService;

@Service
public class BenefitServiceImpl implements BenefitService {

    @Autowired
    private BenefitRepository benefitRepository;

    public BenefitServiceImpl(BenefitRepository benefitRepository) {
        this.benefitRepository = benefitRepository;
    }

    @Override
    public List<Benefit> getAll() {
        return benefitRepository.findAll();
    }

    @Override
    public Benefit saveBenefit(Benefit benefit) {
        return benefitRepository.save(benefit);
    }

    @Override
    public Benefit updateBenefit(Benefit benefit) {
        if (benefit.getId() == null) {
            throw new InvalidDataGivenException("ID was not given!");
        }

        Benefit benefitDb = benefitRepository.findById(benefit.getId()).orElseThrow(() -> new ObjectNotFoundException("Tax with given ID could not be found!"));

        if (benefit.getCost() != null) benefitDb.setCost(benefit.getCost());
        if (benefit.getName() != null) benefitDb.setName(benefit.getName());

        return benefitRepository.save(benefitDb);
    }

    @Override
    public void deleteBenefit(Long benefitId) {
        Benefit benefit = benefitRepository.findById(benefitId).orElseThrow(() -> new ObjectNotFoundException("Tax with given ID could not be found!"));
        benefitRepository.delete(benefit);
    }
    
}
