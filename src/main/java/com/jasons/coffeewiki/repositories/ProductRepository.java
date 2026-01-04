package com.jasons.coffeewiki.repositories;

import com.jasons.coffeewiki.entities.ProductEntity;
import com.jasons.coffeewiki.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    public List<ProductEntity> getProductsByCompanyCode(String companyCode);
    public ProductEntity getProductByCode(String code);
}
