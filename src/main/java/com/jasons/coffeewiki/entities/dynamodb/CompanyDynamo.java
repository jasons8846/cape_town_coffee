package com.jasons.coffeewiki.entities.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class CompanyDynamo {
    private String name;
    private String code;
    private Boolean active;

    public CompanyDynamo() {
    }

    public CompanyDynamo(String name, String code, Boolean active) {
        this.name = name;
        this.code = code;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDbPartitionKey
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString(){
        return "{Company:{ code: " + code + ", name: " + name + "}}";
    }
}
