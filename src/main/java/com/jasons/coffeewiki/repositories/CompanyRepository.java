package com.jasons.coffeewiki.repositories;

import com.jasons.coffeewiki.entities.CompanyEntity;
import com.jasons.coffeewiki.entities.dynamodb.CompanyDynamo;
import com.jasons.coffeewiki.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository {


    public List<CompanyDynamo> getCompanyByNameContaining(String name);
    public List<CompanyDynamo> findAll();
    public CompanyDynamo getCompanyByCode(String code);
    public CompanyDynamo save(CompanyDynamo companyDynamo);
}
