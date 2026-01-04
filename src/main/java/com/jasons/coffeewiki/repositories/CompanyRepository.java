package com.jasons.coffeewiki.repositories;

import com.jasons.coffeewiki.entities.CompanyEntity;
import com.jasons.coffeewiki.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {


    public CompanyEntity getCompanyByNameContaining(String name);
    public CompanyEntity getCompanyByCode(String code);
}
