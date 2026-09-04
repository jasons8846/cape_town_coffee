package com.jasons.coffeewiki.controllers;

import com.jasons.coffeewiki.entities.CompanyEntity;
import com.jasons.coffeewiki.entities.ProductEntity;
import com.jasons.coffeewiki.entities.dynamodb.Company;
import com.jasons.coffeewiki.entities.dynamodb.ProductDynamo;
import com.jasons.coffeewiki.repositories.CompanyRepository;
import com.jasons.coffeewiki.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MigrationController {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ProductRepository productRepository;

    private final DynamoDbTable<Company> companyTable;
    private final DynamoDbTable<ProductDynamo> productTable;

    public MigrationController(DynamoDbTable<Company> companyTable, DynamoDbTable<ProductDynamo> productTable) {
        this.companyTable = companyTable;
        this.productTable = productTable;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/migration/company")
    ResponseEntity<String> migrateCompany(){
        List<CompanyEntity> companyEntityList = new ArrayList<>();
        Integer compCount = 0;
        companyEntityList = companyRepository.findAll();
        Integer totalSize = companyEntityList.size();

        System.out.println("Total companies: " + totalSize);

        for(CompanyEntity companyEntity : companyEntityList){
            compCount++;
            System.out.println("Company: " + compCount + " / " + totalSize);

            Company company = new Company();
            company.setCode(companyEntity.getCode());
            company.setName(companyEntity.getName());
            company.setActive(true);

            companyTable.putItem(company);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body("Company Data Migrated to DynamoDB");
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/migration/product")
    ResponseEntity<String> migrateProduct(){
        List<ProductEntity> productEntityList = new ArrayList<>();
        Integer compCount = 0;
        productEntityList = productRepository.findAll();
        Integer totalSize = productEntityList.size();

        System.out.println("Total products: " + totalSize);
        for(ProductEntity productEntity : productEntityList){
            compCount++;
            System.out.println("Product: " + compCount + " / " + totalSize);

            ProductDynamo productDynamo = new ProductDynamo();
            productDynamo.setCode(productEntity.getCode());
            productDynamo.setCompanyCode(productEntity.getCompanyCode());
            productDynamo.setName(productEntity.getName());
            productDynamo.setCurrency(productEntity.getCurrency());
            productDynamo.setPrice(productEntity.getPrice());
            productDynamo.setSequence(productEntity.getSequence());
            productDynamo.setActive(productEntity.getActive());

            Map<String, String> variant = new HashMap<>();
            Map<String, String> size = new HashMap<>();

            if(productEntity.getProductVariant() != null){
                variant.put("description", productEntity.getProductVariant().getDescription());
                if(productEntity.getProductVariant().getSequence() != null) {
                    variant.put("sequence", productEntity.getProductVariant().getSequence().toString());
                }
                productDynamo.setProductVariant(variant);
            }

            if(productEntity.getProductSize() != null){
                size.put("description", productEntity.getProductSize().getDescription());
                if(productEntity.getProductSize().getSequence() != null) {
                    size.put("sequence", productEntity.getProductSize().getSequence().toString());
                }

                productDynamo.setProductSize(size);
            }

            productTable.putItem(productDynamo);


        }

        return ResponseEntity.status(HttpStatus.OK)
                .body("Company Data Migrated to DynamoDB");
    }

}
