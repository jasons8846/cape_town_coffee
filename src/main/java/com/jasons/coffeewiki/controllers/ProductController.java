package com.jasons.coffeewiki.controllers;

import com.jasons.coffeewiki.api.ProductsApi;
import com.jasons.coffeewiki.entities.dynamodb.ProductDynamo;
import com.jasons.coffeewiki.entities.ProductEntity;
import com.jasons.coffeewiki.model.*;
import com.jasons.coffeewiki.services.ProductService;
import com.jasons.coffeewiki.supportfunctions.CursorCrypto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class ProductController implements ProductsApi {

    @Autowired
    ProductService productService;

    @Autowired
    CursorCrypto cursorCrypto;

    private static final Logger log =
            LoggerFactory.getLogger(CompanyController.class);

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DeleteProductResponseWrapper> deleteProduct(String productCode,String xCorrelationId) {
        log.info("CorrletationId: " + xCorrelationId +   " || DELETE /v1/product by product code initiated");
        DeleteProductResponseWrapper wrapper = new DeleteProductResponseWrapper();
        DeleteProductResponse response = new DeleteProductResponse();

        response.setMessage(productService.deleteProduct(productCode));
        wrapper.data(response);
        wrapper.setError(null);

        log.info("CorrletationId: " + xCorrelationId +   " || DELETE /v1/product by product code successful");

        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PostMapping("/test-product-dynamodb")
//    @SecurityRequirement(name = "bearerAuth")
//    public ResponseEntity<String> saveProductToDynamo(@RequestBody ProductDynamo product){
//        productService.saveProductTest(product);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body("Product saved");
//
//    }


        @Override
    public ResponseEntity<GetCpyProductResponseWrapper> retrieveCpyProducts(String companyCode, Integer pageSize, String xCorrelationId, String cursor) {

        log.info("CorrletationId: " + xCorrelationId +   " || GET /v1/product by company code initiated");

            Integer parseCursor = 0;

            if(cursor == null){
                parseCursor  = 0;
            }else{
                System.out.print("Decrypting");
                parseCursor = Integer.valueOf(cursorCrypto.decrypt(cursor));
            }


        List<ProductEntity> productEntities = productService.getCompanyProducts(companyCode, parseCursor, pageSize);

        GetCpyProductResponseWrapper wrapper = new GetCpyProductResponseWrapper();
        GetCpyProductResponse response = new GetCpyProductResponse();
        List<Product> products = new ArrayList<>();

            productEntities.forEach(productEntity -> {
            Product product = new Product();


            product.setName(productEntity.getName());
            product.setCompanyCode(productEntity.getCompanyCode());
            product.setPrice(productEntity.getPrice());
            product.setVariant(productEntity.getProductVariant());
            product.setCurrency(productEntity.getCurrency());
            product.setSequence(productEntity.getSequence());
            product.setCode(productEntity.getCode());
            product.setSize(productEntity.getProductSize());

            products.add(product);
        });

            if(products.size() > pageSize) {

                response.setProducts(products
                        .stream()
                        .limit(products.size() - 1)
                        .collect(Collectors.toList()));
                response.setNextCursor(cursorCrypto.encrypt(String.valueOf(productEntities.get(productEntities.size()-1).getId())));

            }else{
                response.setProducts(products
                        .stream()
                        .limit(products.size())
                        .collect(Collectors.toList()));
                response.setNextCursor(null);
            }



        wrapper.setData(response);
        wrapper.setError(null);

        log.info("CorrletationId: " + xCorrelationId +   " || GET /v1/product by company code successful");
        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);
    }


    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SaveProductResponseWrapper> saveProduct(String xCorrelationId,SaveProductRequestWrapper saveProductRequestWrapper) {

        log.info("CorrletationId: " + xCorrelationId +   " || POST /v1/product initiated");
        SaveProductResponseWrapper wrapper = new SaveProductResponseWrapper();
        SaveProductResponse response = new SaveProductResponse();


        response.setMessage(productService.saveProduct(saveProductRequestWrapper.getData()));

        wrapper.setError(null);
        wrapper.setData(response);
        log.info("CorrletationId: " + xCorrelationId +   " || POST /v1/product successful");
        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);
    }


    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UpdateProductResponseWrapper> updateProduct(String productCode, String xCorrelationId, UpdateProductRequestWrapper updateProductRequestWrapper) {
        log.info("CorrletationId: " + xCorrelationId +   " || PUT /v1/product initiated");
        UpdateProductResponseWrapper wrapper = new UpdateProductResponseWrapper();
        UpdateProductResponse response = new UpdateProductResponse();

        response.setMessage(productService.updateProduct(productCode, updateProductRequestWrapper.getData()));
        wrapper.data(response);
        wrapper.setError(null);

        log.info("CorrletationId: " + xCorrelationId +   " || PUT /v1/product successful");
        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);
    }
}
