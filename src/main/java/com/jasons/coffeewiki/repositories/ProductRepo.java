package com.jasons.coffeewiki.repositories;

import com.jasons.coffeewiki.entities.ProductEntity;
import com.jasons.coffeewiki.entities.dynamodb.CompanyDynamo;
import com.jasons.coffeewiki.entities.dynamodb.ProductDynamo;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;

@Repository
public class ProductRepo implements ProductRepository{
    private final DynamoDbTable<ProductDynamo> productTable;

    public ProductRepo(DynamoDbTable<ProductDynamo> productTable) {
        this.productTable = productTable;
    }

    @Override
    public Page<ProductDynamo> getProductsByCompanyCode(String companyCode, Map<String, AttributeValue> cursor, Integer pageSize) {

        System.out.println("Repo Page Size:" + pageSize);
        QueryConditional condition = QueryConditional.keyEqualTo(
                Key.builder()
                        .partitionValue(companyCode)
                        .build()
        );

        if (cursor.isEmpty()){
            QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                    .queryConditional(condition)
                    .limit(pageSize)
                    .exclusiveStartKey(null)
                    .build();

            return productTable
                    .index("companyCode-index")
                    .query(request)
                    .stream()
                    .findFirst()
                    .orElse(null);
        }else {
            QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                    .queryConditional(condition)
                    .limit(pageSize)
                    .exclusiveStartKey(cursor)
                    .build();

            return productTable
                    .index("companyCode-index")
                    .query(request)
                    .stream()
                    .findFirst()
                    .orElse(null);
        }




//        return productTable.scan().
//                        items()
//                        .stream()
//                        .filter(product ->
//                        product.getCompanyCode().equals(companyCode))
//                        .limit(pageSize)
//                        .toList();
    }

    @Override
    public ProductDynamo getProductByCode(String code) {
        return productTable.scan()
                .items()
                .stream()
                .filter(productDynamo ->
                        productDynamo.getCode().equals(code))
                .findFirst()
                .orElse(null);

    }

    @Override
    public ProductDynamo save(ProductDynamo productDynamo) {
        productTable.putItem(productDynamo);
        return productDynamo;
    }


}
