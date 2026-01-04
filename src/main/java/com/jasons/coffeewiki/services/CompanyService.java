package com.jasons.coffeewiki.services;

import com.jasons.coffeewiki.entities.CompanyEntity;
import com.jasons.coffeewiki.model.Company;
import com.jasons.coffeewiki.model.SaveCompanyResponse;

import java.util.List;

public interface CompanyService {

    public CompanyEntity getCompanyByName(String name);
    public List<CompanyEntity> getAllCompanies();
    public String saveCompany(String name);
    public String updateCompany(Company company);
    public String deleteCompany(String code);
}
