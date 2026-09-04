package com.jasons.coffeewiki.entities.dynamodb;

import com.jasons.coffeewiki.model.ProductSize;
import com.jasons.coffeewiki.model.ProductVariant;
import jakarta.persistence.Entity;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;
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


    public ProductDynamo() {
    }

    @DynamoDbPartitionKey
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
}
