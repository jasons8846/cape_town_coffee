package com.jasons.coffeewiki.controllers;

import com.jasons.coffeewiki.api.ProductsApi;
import com.jasons.coffeewiki.exceptions.NotFoundException;
import com.jasons.coffeewiki.model.*;
import com.jasons.coffeewiki.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ProductController implements ProductsApi {

    @Autowired
    ProductService productService;

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

    @Override
    public ResponseEntity<GetCpyProductResponseWrapper> retrieveCpyProducts(String companyCode, String xCorrelationId) {

        log.info("CorrletationId: " + xCorrelationId +   " || GET /v1/product by company code initiated");
        List<Product> products = new ArrayList<>();

        GetCpyProductResponseWrapper wrapper = new GetCpyProductResponseWrapper();

        wrapper.setData(productService.getCompanyProducts(companyCode));
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
    public ResponseEntity<UpdateProductResponseWrapper> updateProduct(String xCorrelationId, UpdateProductRequestWrapper updateProductRequestWrapper) {
        log.info("CorrletationId: " + xCorrelationId +   " || PUT /v1/product initiated");
        UpdateProductResponseWrapper wrapper = new UpdateProductResponseWrapper();
        UpdateProductResponse response = new UpdateProductResponse();

        response.setMessage(productService.updateProduct(updateProductRequestWrapper.getData()));
        wrapper.data(response);
        wrapper.setError(null);

        log.info("CorrletationId: " + xCorrelationId +   " || PUT /v1/product successful");
        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);
    }
}
