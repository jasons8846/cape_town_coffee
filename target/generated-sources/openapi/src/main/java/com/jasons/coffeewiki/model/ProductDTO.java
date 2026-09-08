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
 * ProductDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-07T15:50:50.901633+02:00[Africa/Johannesburg]", comments = "Generator version: 7.25.0")
public class ProductDTO {

  private @Nullable String companyCode;

  private @Nullable String name;

  private @Nullable ProductVariant variant;

  private @Nullable ProductSize size;

  private @Nullable BigDecimal price;

  private @Nullable String currency;

  private @Nullable Integer sequence;

  public ProductDTO companyCode(@Nullable String companyCode) {
    this.companyCode = companyCode;
    return this;
  }

  /**
   * unique code asscociated to each company on creation
   * @return companyCode
   */
  
  @Schema(name = "companyCode", example = "12weHIaq3ATOP1nM3Cx", description = "unique code asscociated to each company on creation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("companyCode")
  public @Nullable String getCompanyCode() {
    return companyCode;
  }

  @JsonProperty("companyCode")
  public void setCompanyCode(@Nullable String companyCode) {
    this.companyCode = companyCode;
  }

  public ProductDTO name(@Nullable String name) {
    this.name = name;
    return this;
  }

  /**
   * the name of the product
   * @return name
   */
  
  @Schema(name = "name", example = "FLAT WHITE", description = "the name of the product", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public @Nullable String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(@Nullable String name) {
    this.name = name;
  }

  public ProductDTO variant(@Nullable ProductVariant variant) {
    this.variant = variant;
    return this;
  }

  /**
   * Get variant
   * @return variant
   */
  @Valid 
  @Schema(name = "variant", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("variant")
  public @Nullable ProductVariant getVariant() {
    return variant;
  }

  @JsonProperty("variant")
  public void setVariant(@Nullable ProductVariant variant) {
    this.variant = variant;
  }

  public ProductDTO size(@Nullable ProductSize size) {
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

  public ProductDTO price(@Nullable BigDecimal price) {
    this.price = price;
    return this;
  }

  /**
   * The price amount in the specified currency.
   * @return price
   */
  @Valid 
  @Schema(name = "price", example = "34.99", description = "The price amount in the specified currency.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("price")
  public @Nullable BigDecimal getPrice() {
    return price;
  }

  @JsonProperty("price")
  public void setPrice(@Nullable BigDecimal price) {
    this.price = price;
  }

  public ProductDTO currency(@Nullable String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * The currency code (e.g., ZAR, USD).
   * @return currency
   */
  
  @Schema(name = "currency", example = "ZAR", description = "The currency code (e.g., ZAR, USD).", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("currency")
  public @Nullable String getCurrency() {
    return currency;
  }

  @JsonProperty("currency")
  public void setCurrency(@Nullable String currency) {
    this.currency = currency;
  }

  public ProductDTO sequence(@Nullable Integer sequence) {
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
    ProductDTO productDTO = (ProductDTO) o;
    return Objects.equals(this.companyCode, productDTO.companyCode) &&
        Objects.equals(this.name, productDTO.name) &&
        Objects.equals(this.variant, productDTO.variant) &&
        Objects.equals(this.size, productDTO.size) &&
        Objects.equals(this.price, productDTO.price) &&
        Objects.equals(this.currency, productDTO.currency) &&
        Objects.equals(this.sequence, productDTO.sequence);
  }

  @Override
  public int hashCode() {
    return Objects.hash(companyCode, name, variant, size, price, currency, sequence);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductDTO {\n");
    sb.append("    companyCode: ").append(toIndentedString(companyCode)).append("\n");
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

