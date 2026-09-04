package com.jasons.coffeewiki.services;

import com.jasons.coffeewiki.entities.dynamodb.ProductDynamo;
import com.jasons.coffeewiki.entities.ProductEntity;
import com.jasons.coffeewiki.model.ProductDTO;
import com.jasons.coffeewiki.model.ProductUpdate;

import java.util.List;


public interface ProductService {

    public List<ProductEntity> getCompanyProducts(String companyCode, Integer cursor, Integer pageSize);
    public String saveProduct(ProductDTO product);
    public String updateProduct(String code, ProductUpdate product);
    public String deleteProduct(String code);
//    public String saveProductTest(ProductDynamo product);

}
