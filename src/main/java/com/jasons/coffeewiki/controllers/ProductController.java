package com.jasons.coffeewiki.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasons.coffeewiki.api.ProductsApi;
import com.jasons.coffeewiki.entities.ProductCursor;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

            Map<String, AttributeValue> parseCursor = new HashMap<>();

            if(cursor != null){
                System.out.println("Decrypting");
                System.out.println("Cursor: " + cursor);
                String decryptedCursor = cursorCrypto.decrypt(cursor);
                System.out.println("Decrypted cursor: " + decryptedCursor);
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    ProductCursor productCursor = objectMapper.readValue(decryptedCursor, ProductCursor.class);
                    parseCursor =
                            Map.of(
                                    "companyCode",
                                    AttributeValue.builder()
                                            .s(productCursor.companyCode())
                                            .build(),

                                    "code",
                                    AttributeValue.builder()
                                            .s(productCursor.code())
                                            .build()
                            );
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

            }

//        if(pageSize == 0) {
//            pageSize = 1;
//        };
        Page<ProductDynamo> pageEntity = productService.getCompanyProducts(companyCode, parseCursor, pageSize);
        List<ProductDynamo> productEntities = pageEntity.items();

        GetCpyProductResponseWrapper wrapper = new GetCpyProductResponseWrapper();
        GetCpyProductResponse response = new GetCpyProductResponse();
        List<Product> products = new ArrayList<>();

            productEntities.forEach(productEntity -> {
            Product product = new Product();
            ProductVariant productVariant = new ProductVariant();
            ProductSize productSize = new ProductSize();

            productVariant.setDescription(productEntity.getProductVariant().get("description"));
            productVariant.setSequence(Integer.valueOf(productEntity.getProductVariant().get("sequence")));

            productSize.setDescription(productEntity.getProductSize().get("description"));
            productSize.setSequence(Integer.valueOf(productEntity.getProductSize().get("sequence")));


            product.setName(productEntity.getName());
            product.setCompanyCode(productEntity.getCompanyCode());
            product.setPrice(productEntity.getPrice());
            product.setVariant(productVariant);
            product.setCurrency(productEntity.getCurrency());
            product.setSequence(productEntity.getSequence());
            product.setCode(productEntity.getCode());
            product.setSize(productSize);
            product.setUpdateDate(productEntity.getUpdateDate());

            products.add(product);
        });
            System.out.println("Product list size: " + products.size() + "  pageSize: "+ pageSize);
            if(products.size() > pageSize) {

                List<Product> displayProducts = products
                        .stream()
                        .limit(products.size()-1)
                        .collect(Collectors.toList());

                response.setProducts(displayProducts);

                Product lastProduct = displayProducts.get(displayProducts.size()-1);

                ProductCursor productCursor = new ProductCursor(
                        lastProduct.getCompanyCode(),
                        lastProduct.getCode()
                );
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    String json = objectMapper.writeValueAsString(productCursor);

                    response.setNextCursor(
                            cursorCrypto.encrypt(
                                    json
                            ));


                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
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
