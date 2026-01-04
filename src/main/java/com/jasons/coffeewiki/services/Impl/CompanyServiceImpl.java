package com.jasons.coffeewiki.services.Impl;

import com.jasons.coffeewiki.entities.CompanyEntity;
import com.jasons.coffeewiki.exceptions.DataNotSavedException;
import com.jasons.coffeewiki.exceptions.NotFoundException;
import com.jasons.coffeewiki.model.Company;
import com.jasons.coffeewiki.repositories.CompanyRepository;
import com.jasons.coffeewiki.services.CompanyService;
import com.jasons.coffeewiki.supportfunctions.RandomTextGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CompanyServiceImpl implements CompanyService {

    private static final Logger log =
            LoggerFactory.getLogger(CompanyServiceImpl.class);
    @Autowired
    CompanyRepository companyRepository;

    @Override
    public CompanyEntity getCompanyByName(String name) {

        CompanyEntity entity = companyRepository.getCompanyByNameContaining(name);
        if(entity == null || entity.getActive() == false){
            log.warn("Get company by name: No company data is available for the request");
            throw new NotFoundException("No company data is available for the request");
        }

        return entity;
    }

    @Override
    public List<CompanyEntity> getAllCompanies() {
        List<CompanyEntity> entities = new ArrayList<>();
        entities = companyRepository.findAll()
                .stream()
                .filter(e -> e.getActive() == true)
                .collect(Collectors.toList());

        if(entities.isEmpty()){
            log.warn("Get all companies: No company data is available for the request");
            throw new NotFoundException("No company data is available for the request");
        }else{
            return entities;
        }
    }

    @Override
    public String saveCompany(String name) {
        CompanyEntity entity = new CompanyEntity();

        try{
            entity.setName(name);
            entity.setCode(new RandomTextGenerator().generateRandomText(20));
            entity.setActive(true);
            companyRepository.save(entity);


        }catch (Exception ex){
            log.warn("Save company: Company could not be saved");
            throw new DataNotSavedException("Company could not be saved");
        }

        return entity.getCode();
    }

    @Override
    public String updateCompany(Company company) {


        CompanyEntity entity = companyRepository.getCompanyByCode(company.getCode());
        if(entity == null || entity.getActive() == false){
            log.warn("Update company: Company code " + company.getCode() + " is not valid");
            throw new NotFoundException("Company code " + company.getCode() + " is not valid");
        }

        entity.setName(company.getName());

        companyRepository.save(entity);

        return "Company details updated";
    }

    @Override
    public String deleteCompany(String code) {

        CompanyEntity entity = companyRepository.getCompanyByCode(code);
        if(entity == null || entity.getActive() == false){
            log.warn("Delete company: Company code " + code + " is not valid");
            throw new NotFoundException("Company code " + code + " is not valid");
        }

        entity.setActive(false);

        companyRepository.save(entity);

        return "Company deleted";
    }

}
