package com.jasons.coffeewiki.services.Impl;

import com.jasons.coffeewiki.controllers.CompanyController;
import com.jasons.coffeewiki.entities.CompanyEntity;
import com.jasons.coffeewiki.entities.ProductCursor;
import com.jasons.coffeewiki.entities.dynamodb.ProductDynamo;
import com.jasons.coffeewiki.entities.ProductEntity;
import com.jasons.coffeewiki.exceptions.DataNotSavedException;
import com.jasons.coffeewiki.exceptions.FieldRequiredException;
import com.jasons.coffeewiki.exceptions.NotFoundException;
import com.jasons.coffeewiki.model.*;
import com.jasons.coffeewiki.repositories.CompanyRepository;
import com.jasons.coffeewiki.repositories.ProductRepository;
import com.jasons.coffeewiki.services.ProductService;
import com.jasons.coffeewiki.supportfunctions.RandomTextGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    ProductRepository productRepository;

    @Autowired
    CompanyRepository companyRepository;

    private static final Logger log =
            LoggerFactory.getLogger(CompanyController.class);

//    private final DynamoDbTable<ProductDynamo> productTable;
//
//    public ProductServiceImpl(DynamoDbTable<ProductDynamo> productTable) {
//        this.productTable = productTable;
//    }
//
//    @Override
//    public String saveProductTest(ProductDynamo product) {
//        product.setCode(UUID.randomUUID().toString());
//        productRepository.save(product);
//        return "Prod";
//    }


    @Override
    public Page<ProductDynamo> getCompanyProducts(String companyCode, Map<String, AttributeValue> cursor, Integer pageSize) {

        if(ValidateCompanyCode(companyCode) == false){
            log.warn("Get company products: Company code " + companyCode + " is not valid");
            throw new NotFoundException("Company code " + companyCode + " is not valid");
        }


        Page<ProductDynamo> productEntities = productRepository.getProductsByCompanyCode(companyCode, cursor, pageSize+1);

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

            ProductDynamo entity = new ProductDynamo(new RandomTextGenerator().generateRandomText(30), product.getCompanyCode() ,product.getName(), product.getPrice(), product.getCurrency(), product.getSequence());
            Map<String, String> productVariant = new HashMap<>();
            Map<String, String> productSize = new HashMap<>();

            if(product.getVariant().getSequence() != null || product.getVariant().getDescription() != null) {
//                productVariant.setDescription(product.getVariant().getDescription());
//                productVariant.setSequence(product.getVariant().getSequence());

                productVariant.put("description", product.getVariant().getDescription());
                productVariant.put("sequence", String.valueOf(product.getVariant().getSequence()));

                entity.setProductVariant(productVariant);
            }

            productSize.put("description", product.getSize().getDescription());
            productSize.put("sequence", String.valueOf(product.getSize().getSequence()));

            entity.setProductSize(productSize);
            entity.setActive(true);
            entity.setUpdateDate(Instant.now());

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

        ProductDynamo entity = productRepository.getProductByCode(code);
        if(entity == null || entity.getActive() == false){
            throw new NotFoundException("Product code " + code + " is not valid");
        }

        Map<String, String> productVariant = new HashMap<>();
        productVariant.put("description", product.getVariant().getDescription());
        productVariant.put("sequence", String.valueOf(product.getVariant().getSequence()));
//
        entity.setPrice(product.getPrice());
        entity.setName(product.getName());
//        entity.setProductVariant(productVariant);
        entity.setCurrency(product.getCurrency());
        entity.setSequence(product.getSequence());

        productRepository.save(entity);

        return "Product updated";
    }

    @Override
    public String deleteProduct(String code) {
        ProductDynamo entity = productRepository.getProductByCode(code);
        if(entity == null || entity.getActive() == false){
            throw new NotFoundException("Product code " + code + " is not valid");
        }
//
        entity.setActive(false);

        productRepository.save(entity);

        return "Product deleted";
    }



    private boolean ValidateCompanyCode(String companyCode){
//        CompanyEntity entity = companyRepository.getCompanyByCode(companyCode);

//        if(entity == null || entity.getActive() == false){
//            return false;
//        }
           return true;
    };
}
