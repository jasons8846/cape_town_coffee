package com.jasons.coffeewiki.repositories;

import com.jasons.coffeewiki.entities.ProductEntity;
import com.jasons.coffeewiki.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Query(value = "SELECT * FROM product where company_code = :cpyCode and id >= :cursor and active = 1 limit :pageSize",
            nativeQuery = true
    )
    public List<ProductEntity> getProductsByCompanyCode(@Param("cpyCode") String companyCode, @Param("cursor") Integer cursor, @Param("pageSize") Integer pageSize);
    public ProductEntity getProductByCode(String code);
}
