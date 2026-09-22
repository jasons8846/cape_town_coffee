package com.jasons.coffeewiki.services;

import com.jasons.coffeewiki.entities.dynamodb.ProductDynamo;
import com.jasons.coffeewiki.entities.ProductEntity;
import com.jasons.coffeewiki.model.ProductDTO;
import com.jasons.coffeewiki.model.ProductUpdate;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;


public interface ProductService {

    public Page<ProductDynamo> getCompanyProducts(String companyCode, Map<String, AttributeValue> cursor, Integer pageSize);
    public String saveProduct(ProductDTO product);
    public String updateProduct(String code, ProductUpdate product);
    public String deleteProduct(String code);
//    public String saveProductTest(ProductDynamo product);

}
