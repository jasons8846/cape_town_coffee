package com.jasons.coffeewiki.services;

import com.jasons.coffeewiki.entities.CompanyEntity;
import com.jasons.coffeewiki.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    public Optional<CompanyEntity> getCompanyByName(String name);
    public List<CompanyEntity> getAllCompanies();
    public String saveCompany(String name);
    public String updateCompany(Company company);
    public String deleteCompany(String code);
}
