package com.jasons.coffeewiki.repositories;

import com.jasons.coffeewiki.entities.ProductEntity;
import com.jasons.coffeewiki.entities.dynamodb.ProductDynamo;
import com.jasons.coffeewiki.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;

public interface ProductRepository {

    @Query(value = "SELECT * FROM product where company_code = :cpyCode and id >= :cursor and active = 1 limit :pageSize",
            nativeQuery = true
    )
    public Page<ProductDynamo> getProductsByCompanyCode(@Param("cpyCode") String companyCode, @Param("cursor") Map<String, AttributeValue> cursor, @Param("pageSize") Integer pageSize);
    public ProductDynamo getProductByCode(String code);
    public ProductDynamo save(ProductDynamo productDynamo);
}
