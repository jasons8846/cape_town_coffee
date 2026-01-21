package com.jasons.coffeewiki.services.Impl;

import com.jasons.coffeewiki.controllers.CompanyController;
import com.jasons.coffeewiki.entities.CompanyEntity;
import com.jasons.coffeewiki.entities.ProductEntity;
import com.jasons.coffeewiki.entities.ProductVariantEntity;
import com.jasons.coffeewiki.exceptions.DataNotSavedException;
import com.jasons.coffeewiki.exceptions.FieldRequiredException;
import com.jasons.coffeewiki.exceptions.NotFoundException;
import com.jasons.coffeewiki.model.Product;
import com.jasons.coffeewiki.model.ProductDTO;
import com.jasons.coffeewiki.model.ProductUpdate;
import com.jasons.coffeewiki.model.ProductVariant;
import com.jasons.coffeewiki.repositories.CompanyRepository;
import com.jasons.coffeewiki.repositories.ProductRepository;
import com.jasons.coffeewiki.services.ProductService;
import com.jasons.coffeewiki.supportfunctions.CursorCrypto;
import com.jasons.coffeewiki.supportfunctions.RandomTextGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    ProductRepository productRepository;

    @Autowired
    CompanyRepository companyRepository;

    private static final Logger log =
            LoggerFactory.getLogger(CompanyController.class);

    @Override
    public List<ProductEntity> getCompanyProducts(String companyCode, Integer cursor, Integer pageSize) {

        if(ValidateCompanyCode(companyCode) == false){
            log.warn("Get company products: Company code " + companyCode + " is not valid");
            throw new NotFoundException("Company code " + companyCode + " is not valid");
        }


        List<ProductEntity> productEntities = productRepository.getProductsByCompanyCode(companyCode, cursor, pageSize+1)
                .stream()
                .collect(Collectors.toList());

        if(productEntities == null){
            log.warn("Get company products: No products available for company code " + companyCode);
            throw new NotFoundException("No products available for company code " + companyCode);
        }

        return productEntities;
    }

    @Override
    public String saveProduct(ProductDTO product) {

        if(product.getCompanyCode().isBlank() || product.getCompanyCode().isEmpty()){
            log.warn("Save company product: Company Code is required");
            throw new FieldRequiredException("Company Code is required");
        }

        if(product.getName().isBlank() || product.getName().isEmpty()){
            log.warn("Save company product: Product name is required");
            throw new FieldRequiredException("Product name is required");
        }

        if(product.getVariant().toString() == null){
            log.warn("Save company product: Product variant is required");
            throw new FieldRequiredException("Product variant is required");
        }

        if( product.getPrice() == null || product.getPrice().equals(0)){
            log.warn("Save company product: Product price is required");
            throw new FieldRequiredException("Product price is required");
        }

        if(product.getCurrency().isBlank() || product.getCurrency().isEmpty()){
            log.warn("Save company product: Currency is required");
            throw new FieldRequiredException("Currency is required");
        }

        if(ValidateCompanyCode(product.getCompanyCode()) == false){
            log.warn("Save company product: Company code " + product.getCompanyCode() + " is not valid");
            throw new NotFoundException("Company code " + product.getCompanyCode() + " is not valid");
        }

            ProductEntity entity = new ProductEntity(new RandomTextGenerator().generateRandomText(30), product.getCompanyCode() ,product.getName(), product.getPrice(), product.getCurrency(), product.getSequence());
            ProductVariant productVariant = new ProductVariant();

            productVariant.setDescription(product.getVariant().getDescription());
            productVariant.setSequence(product.getVariant().getSequence());

            entity.setProductVariant(productVariant);
            entity.setActive(true);

            try {

                productRepository.save(entity);
                return "Product saved successfully";
            }catch (Exception ex){
                log.warn("Save company product: Product not saved");
                throw new DataNotSavedException("Product not saved");
            }



    }

    @Override
    public String updateProduct(String code, ProductUpdate product) {

        ProductEntity entity = productRepository.getProductByCode(code);
        if(entity == null || entity.getActive() == false){
            throw new NotFoundException("Product code " + code + " is not valid");
        }

        entity.setPrice(product.getPrice());
        entity.setName(product.getName());
        entity.setProductVariant(product.getVariant());
        entity.setCurrency(product.getCurrency());
        entity.setSequence(product.getSequence());

        productRepository.save(entity);

        return "Product updated";
    }

    @Override
    public String deleteProduct(String code) {
        ProductEntity entity = productRepository.getProductByCode(code);
        if(entity == null || entity.getActive() == false){
            throw new NotFoundException("Product code " + code + " is not valid");
        }

        entity.setActive(false);

        productRepository.save(entity);

        return "Product deleted";
    }

    private boolean ValidateCompanyCode(String companyCode){
        CompanyEntity entity = companyRepository.getCompanyByCode(companyCode);

        if(entity == null || entity.getActive() == false){
            return false;
        }
        return true;
    };
}
