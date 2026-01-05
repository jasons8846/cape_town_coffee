package com.jasons.coffeewiki.controllers;

import com.jasons.coffeewiki.api.CompanyApi;
import com.jasons.coffeewiki.entities.CompanyEntity;
import com.jasons.coffeewiki.model.*;
import com.jasons.coffeewiki.services.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class CompanyController implements CompanyApi {

    @Autowired
    CompanyService companyService;

    private static final Logger log =
            LoggerFactory.getLogger(CompanyController.class);


    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DeleteCompanyResponseWrapper> deleteCompany(String companyCode, String xCorrelationId) {
        log.info("CorrletationId: " + xCorrelationId +   " || DELETE /v1/company by company code initiated");
        DeleteCompanyResponseWrapper wrapper = new DeleteCompanyResponseWrapper();
        DeleteCompanyResponse response = new DeleteCompanyResponse();

        response.setMessage(companyService.deleteCompany(companyCode));

        wrapper.setData(response);
        wrapper.setError(null);
        log.info("CorrletationId:" + xCorrelationId +   " || DELETE /v1/company by company code successful");
        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UpdateCompanyResponseWrapper> updateCompany(String xCorrelationId,  UpdateCompanyRequestWrapper updateCompanyRequestWrapper) {
        log.info("CorrletationId: " + xCorrelationId +   " || PUT /v1/company initiated");
        UpdateCompanyResponseWrapper wrapper = new UpdateCompanyResponseWrapper();
        UpdateCompanyResponse response = new UpdateCompanyResponse();

        response.setMessage(companyService.updateCompany(updateCompanyRequestWrapper.getData()));

        wrapper.setData(response);
        wrapper.setError(null);
        log.info("CorrletationId: " + xCorrelationId +   " || PUT /v1/company successful");
        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);
    }



    @Override
    public ResponseEntity<GetCompanyNameResponseWrapper> getCompanyName(String name, String xCorrelationId) {
        log.info("CorrletationId: " + xCorrelationId +   " || GET /v1/company by name initiated");
        Company company = new Company();
        Optional<CompanyEntity> entity = companyService.getCompanyByName(name);

        company.setName(entity.get().getName());
        company.setCode(entity.get().getCode());

        GetCompanyNameResponseWrapper wrapper = new GetCompanyNameResponseWrapper();
        wrapper.setData(company);
        wrapper.setError(null);

        log.info("CorrletationId: " + xCorrelationId +   " || GET /v1/company by name successfull");
        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);

    }


    @Override
    public ResponseEntity<GetAllCompaniesResponseWrapper> getAllCompanies(String xCorrelationId) {
        log.info("CorrletationId: " + xCorrelationId +   " || GET /v1/company initiated");
        List<Company> companies = new ArrayList<>();


        companyService.getAllCompanies().forEach(companyEntity -> {
            Company company = new Company();
            company.setName(companyEntity.getName());
            company.setCode(companyEntity.getCode());
            companies.add(company);
        });

        GetAllCompaniesResponseWrapper wrapper = new GetAllCompaniesResponseWrapper();

        wrapper.setData(companies);
        wrapper.setError(null);

        log.info("CorrletationId: " + xCorrelationId +   " || GET /v1/company by name successfull");

        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SaveCompanyResponseWrapper> saveCompany(String xCorrelationId, SaveCompanyRequestWrapper saveCompanyRequestWrapper) {
        log.info("CorrletationId: " + xCorrelationId +   " || POST /v1/company initiated");
        SaveCompanyResponseWrapper wrapper = new SaveCompanyResponseWrapper();
        SaveCompanyResponse response = new SaveCompanyResponse();
        String name = saveCompanyRequestWrapper.getData().getName();

        response.setCompanyCode(companyService.saveCompany(name));
        response.setMessage("Company saved successfully!");
        wrapper.data(response);

        log.info("CorrletationId:" + xCorrelationId +   " || POST /v1/company successfull");
        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);
    }
}
