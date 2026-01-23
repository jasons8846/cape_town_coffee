package com.jasons.coffeewiki.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.jasons.coffeewiki.model.Product;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * GetCpyProductResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-23T13:34:04.987765500+02:00[Africa/Johannesburg]", comments = "Generator version: 7.4.0")
public class GetCpyProductResponse {

  @Valid
  private List<@Valid Product> products;

  private String nextCursor;

  public GetCpyProductResponse products(List<@Valid Product> products) {
    this.products = products;
    return this;
  }

  public GetCpyProductResponse addProductsItem(Product productsItem) {
    if (this.products == null) {
      this.products = new ArrayList<>();
    }
    this.products.add(productsItem);
    return this;
  }

  /**
   * Get products
   * @return products
  */
  @Valid 
  @Schema(name = "products", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("products")
  public List<@Valid Product> getProducts() {
    return products;
  }

  public void setProducts(List<@Valid Product> products) {
    this.products = products;
  }

  public GetCpyProductResponse nextCursor(String nextCursor) {
    this.nextCursor = nextCursor;
    return this;
  }

  /**
   * the next cursor to use
   * @return nextCursor
  */
  
  @Schema(name = "nextCursor", example = "x72ysS3cS9uUi5reeXgs7", description = "the next cursor to use", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nextCursor")
  public String getNextCursor() {
    return nextCursor;
  }

  public void setNextCursor(String nextCursor) {
    this.nextCursor = nextCursor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetCpyProductResponse getCpyProductResponse = (GetCpyProductResponse) o;
    return Objects.equals(this.products, getCpyProductResponse.products) &&
        Objects.equals(this.nextCursor, getCpyProductResponse.nextCursor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(products, nextCursor);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetCpyProductResponse {\n");
    sb.append("    products: ").append(toIndentedString(products)).append("\n");
    sb.append("    nextCursor: ").append(toIndentedString(nextCursor)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

