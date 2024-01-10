package com.mkoper.payroll.service;

import java.util.List;

import com.mkoper.payroll.dto.BenefitDto;
import com.mkoper.payroll.model.Benefit;

public interface BenefitService {
    // get list of all benefits
    public List<Benefit> getAll();

    // get benefit data with given ID
    public BenefitDto getBenefitById(Long benefitId);

    // save benefit to the db
    public Benefit saveBenefit(Benefit benefit);

    // update benefit with given ID
    public Benefit updateBenefit(Benefit benefit);

    // delete benefit with given ID
    public void deleteBenefit(Long benefitId);
}
