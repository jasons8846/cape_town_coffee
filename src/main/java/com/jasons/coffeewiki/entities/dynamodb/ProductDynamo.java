package com.jasons.coffeewiki.entities.dynamodb;

import jakarta.validation.Valid;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Map;

@DynamoDbBean
public class ProductDynamo {

    private String code;
    private String companyCode;
    private String name;
    private Map<String, String> productVariant;
    private Map<String, String> productSize;
    private BigDecimal price;
    private String currency;
    private Boolean active;
    private Integer sequence;
    private Instant updateDate;


    public ProductDynamo() {
    }



    @DynamoDbPartitionKey
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "companyCode-index")
    @DynamoDbAttribute("companyCode")
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(Map<String, String> productVariant) {
        this.productVariant = productVariant;
    }

    public Map<String, String> getProductSize() {
        return productSize;
    }

    public void setProductSize(Map<String, String> productSize) {
        this.productSize = productSize;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public ProductDynamo(String code, String companyCode, String name, @Valid BigDecimal price, String currency, Integer sequence) {
        this.code = code;
        this.companyCode = companyCode;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.sequence = sequence;
    }
    public ProductDynamo(String code, String companyCode, String name, Map<String, String> productVariant, Map<String, String> productSize, BigDecimal price, String currency, Boolean active, Integer sequence) {
        this.code = code;
        this.companyCode = companyCode;
        this.name = name;
        this.productVariant = productVariant;
        this.productSize = productSize;
        this.price = price;
        this.currency = currency;
        this.active = active;
        this.sequence = sequence;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }
}
