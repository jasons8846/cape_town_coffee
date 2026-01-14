package com.jasons.coffeewiki.services;

import com.jasons.coffeewiki.entities.CompanyEntity;
import com.jasons.coffeewiki.entities.ProductEntity;
import com.jasons.coffeewiki.model.Company;
import com.jasons.coffeewiki.model.Product;
import com.jasons.coffeewiki.model.ProductDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductService {

    public List<ProductEntity> getCompanyProducts(String companyCode, Integer cursor, Integer pageSize);
    public String saveProduct(ProductDTO product);
    public String updateProduct(Product product);
    public String deleteProduct(String code);

}
