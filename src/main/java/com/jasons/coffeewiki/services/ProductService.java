package com.jasons.coffeewiki.services;

import com.jasons.coffeewiki.entities.CompanyEntity;
import com.jasons.coffeewiki.entities.ProductEntity;
import com.jasons.coffeewiki.model.Company;
import com.jasons.coffeewiki.model.Product;

import java.util.List;


public interface ProductService {

    public List<Product> getCompanyProducts(String companyCode);
    public String saveProduct(Product product);
    public String updateProduct(Product product);
    public String deleteProduct(String code);

}
