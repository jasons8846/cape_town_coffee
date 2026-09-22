package com.jasons.coffeewiki.repositories;

import com.jasons.coffeewiki.entities.dynamodb.CompanyDynamo;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.Collections;
import java.util.List;

@Repository
public class CompanyRepo implements CompanyRepository{

    private final DynamoDbTable<CompanyDynamo> companyTable;

    public CompanyRepo(DynamoDbTable<CompanyDynamo> companyTable) {
        this.companyTable = companyTable;
    }


    @Override
    public List<CompanyDynamo> getCompanyByNameContaining(String name) {
//        QueryConditional conditional = QueryConditional.keyEqualTo(
//                Key.builder()
//                        .partitionValue(name)
//                        .build()
//        );
        return companyTable.scan()
                .items()
                .stream()
                .filter(companyDynamo ->
                        companyDynamo.getName() != null &&
                        companyDynamo.getName()
                                .toLowerCase()
                                .contains(name.toLowerCase())
                ).toList();
    }

    @Override
    public List<CompanyDynamo> findAll() {
        return companyTable.scan()
                .items()
                .stream()
                .toList();
    }

    @Override
    public CompanyDynamo getCompanyByCode(String code) {
        return companyTable.getItem(
                Key.builder()
                        .partitionValue(code)
                        .build()
        );
    }

    @Override
    public CompanyDynamo save(CompanyDynamo companyDynamo) {
                companyTable.putItem(companyDynamo);
        return companyDynamo;
    }


}
