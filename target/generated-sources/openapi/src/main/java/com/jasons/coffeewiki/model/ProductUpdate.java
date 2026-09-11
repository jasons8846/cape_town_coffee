package com.jasons.coffeewiki.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.jasons.coffeewiki.model.ProductSize;
import com.jasons.coffeewiki.model.ProductVariant;
import java.math.BigDecimal;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ProductUpdate
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-09T14:01:12.178912100+02:00[Africa/Johannesburg]", comments = "Generator version: 7.25.0")
public class ProductUpdate {

  private String name;

  private ProductVariant variant;

  private @Nullable ProductSize size;

  private BigDecimal price;

  private String currency;

  private @Nullable Integer sequence;

  public ProductUpdate() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ProductUpdate(String name, ProductVariant variant, BigDecimal price, String currency) {
    this.name = name;
    this.variant = variant;
    this.price = price;
    this.currency = currency;
  }

  public ProductUpdate name(String name) {
    this.name = name;
    return this;
  }

  /**
   * the name of the product
   * @return name
   */
  @NotNull 
  @Schema(name = "name", example = "FLAT WHITE", description = "the name of the product", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  public ProductUpdate variant(ProductVariant variant) {
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

  @JsonProperty("variant")
  public void setVariant(ProductVariant variant) {
    this.variant = variant;
  }

  public ProductUpdate size(@Nullable ProductSize size) {
    this.size = size;
    return this;
  }

  /**
   * Get size
   * @return size
   */
  @Valid 
  @Schema(name = "size", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("size")
  public @Nullable ProductSize getSize() {
    return size;
  }

  @JsonProperty("size")
  public void setSize(@Nullable ProductSize size) {
    this.size = size;
  }

  public ProductUpdate price(BigDecimal price) {
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

  @JsonProperty("price")
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public ProductUpdate currency(String currency) {
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

  @JsonProperty("currency")
  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public ProductUpdate sequence(@Nullable Integer sequence) {
    this.sequence = sequence;
    return this;
  }

  /**
   * the product sequence as a guide as to where in a list it should be displayed
   * @return sequence
   */
  
  @Schema(name = "sequence", example = "1", description = "the product sequence as a guide as to where in a list it should be displayed", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sequence")
  public @Nullable Integer getSequence() {
    return sequence;
  }

  @JsonProperty("sequence")
  public void setSequence(@Nullable Integer sequence) {
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
    ProductUpdate productUpdate = (ProductUpdate) o;
    return Objects.equals(this.name, productUpdate.name) &&
        Objects.equals(this.variant, productUpdate.variant) &&
        Objects.equals(this.size, productUpdate.size) &&
        Objects.equals(this.price, productUpdate.price) &&
        Objects.equals(this.currency, productUpdate.currency) &&
        Objects.equals(this.sequence, productUpdate.sequence);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, variant, size, price, currency, sequence);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductUpdate {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    variant: ").append(toIndentedString(variant)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
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
  private String toIndentedString(@Nullable Object o) {
    return o == null ? "null" : o.toString().replace("\n", "\n    ");
  }
}

