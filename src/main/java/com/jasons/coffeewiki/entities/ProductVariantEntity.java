package com.jasons.coffeewiki.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product_variant")
public class ProductVariantEntity {

    public ProductVariantEntity(String description, Integer sequence) {
        this.description = description;
        this.sequence = sequence;
    }

    public ProductVariantEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String description;
    Integer sequence;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
