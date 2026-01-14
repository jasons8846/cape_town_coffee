package com.jasons.coffeewiki.entities;

import com.jasons.coffeewiki.model.ProductVariant;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String companyCode;
    private String name;

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }

    //    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "product_variant_id", referencedColumnName = "id")
//    private ProductVariantEntity variant;
    @Column(name="variant")
    @JdbcTypeCode(SqlTypes.JSON)
    private ProductVariant productVariant;
    private BigDecimal price;
    private String currency;
    private Boolean active;

    private Integer sequence;

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ProductEntity() {
    }


    public ProductEntity(String code, String companyCode, String name, BigDecimal price, String currency, Integer sequence) {
        this.code = code;
        this.companyCode = companyCode;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.sequence = sequence;
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public ProductVariantEntity getVariant() {
//        return variant;
//    }
//
//    public void setVariant(ProductVariantEntity variant) {
//        this.variant = variant;
//    }

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
}
