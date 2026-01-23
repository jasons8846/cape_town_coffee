package com.jasons.coffeewiki.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.jasons.coffeewiki.model.ProductVariant;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Product
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-23T11:28:51.247156800+02:00[Africa/Johannesburg]", comments = "Generator version: 7.4.0")
public class Product {

  private String companyCode;

  private String code;

  private String name;

  private ProductVariant variant;

  private BigDecimal price;

  private String currency;

  private Integer sequence;

  public Product() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Product(String companyCode, String name, ProductVariant variant, BigDecimal price, String currency) {
    this.companyCode = companyCode;
    this.name = name;
    this.variant = variant;
    this.price = price;
    this.currency = currency;
  }

  public Product companyCode(String companyCode) {
    this.companyCode = companyCode;
    return this;
  }

  /**
   * unique code asscociated to each company on creation
   * @return companyCode
  */
  @NotNull 
  @Schema(name = "companyCode", example = "12weHIaq3ATOP1nM3Cx", description = "unique code asscociated to each company on creation", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("companyCode")
  public String getCompanyCode() {
    return companyCode;
  }

  public void setCompanyCode(String companyCode) {
    this.companyCode = companyCode;
  }

  public Product code(String code) {
    this.code = code;
    return this;
  }

  /**
   * unique code asscociated to each product
   * @return code
  */
  
  @Schema(name = "code", example = "12weHIaq3ATOP1nM3Cx", description = "unique code asscociated to each product", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Product name(String name) {
    this.name = name;
    return this;
  }

  /**
   * the name of the product
   * @return name
  */
  @NotNull 
  @Schema(name = "name", example = "Flat white", description = "the name of the product", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Product variant(ProductVariant variant) {
    this.variant = variant;
    return this;
  }

  /**
   * Get variant
   * @return variant
  */
  @NotNull @Valid 
  @Schema(name = "variant", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("variant")
  public ProductVariant getVariant() {
    return variant;
  }

  public void setVariant(ProductVariant variant) {
    this.variant = variant;
  }

  public Product price(BigDecimal price) {
    this.price = price;
    return this;
  }

  /**
   * The price amount in the specified currency.
   * @return price
  */
  @NotNull @Valid 
  @Schema(name = "price", example = "34.99", description = "The price amount in the specified currency.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("price")
  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Product currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * The currency code (e.g., ZAR, USD).
   * @return currency
  */
  @NotNull 
  @Schema(name = "currency", example = "ZAR", description = "The currency code (e.g., ZAR, USD).", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("currency")
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Product sequence(Integer sequence) {
    this.sequence = sequence;
    return this;
  }

  /**
   * the product sequence as a guide as to where in a list it should be displayed
   * @return sequence
  */
  
  @Schema(name = "sequence", example = "1", description = "the product sequence as a guide as to where in a list it should be displayed", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sequence")
  public Integer getSequence() {
    return sequence;
  }

  public void setSequence(Integer sequence) {
    this.sequence = sequence;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(this.companyCode, product.companyCode) &&
        Objects.equals(this.code, product.code) &&
        Objects.equals(this.name, product.name) &&
        Objects.equals(this.variant, product.variant) &&
        Objects.equals(this.price, product.price) &&
        Objects.equals(this.currency, product.currency) &&
        Objects.equals(this.sequence, product.sequence);
  }

  @Override
  public int hashCode() {
    return Objects.hash(companyCode, code, name, variant, price, currency, sequence);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Product {\n");
    sb.append("    companyCode: ").append(toIndentedString(companyCode)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    variant: ").append(toIndentedString(variant)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    sequence: ").append(toIndentedString(sequence)).append("\n");
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

