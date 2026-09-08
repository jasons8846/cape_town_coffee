package com.jasons.coffeewiki.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ProductVariant
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-08T09:50:26.188626300+02:00[Africa/Johannesburg]", comments = "Generator version: 7.25.0")
public class ProductVariant {

  private @Nullable String description;

  private @Nullable Integer sequence;

  public ProductVariant description(@Nullable String description) {
    this.description = description;
    return this;
  }

  /**
   * describes the variant of the product
   * @return description
   */
  
  @Schema(name = "description", example = "SINGLE", description = "describes the variant of the product", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public @Nullable String getDescription() {
    return description;
  }

  @JsonProperty("description")
  public void setDescription(@Nullable String description) {
    this.description = description;
  }

  public ProductVariant sequence(@Nullable Integer sequence) {
    this.sequence = sequence;
    return this;
  }

  /**
   * the varaint sequence as a guide as to where in a list it should be displayed in relation to other variants of the same product
   * @return sequence
   */
  
  @Schema(name = "sequence", example = "1", description = "the varaint sequence as a guide as to where in a list it should be displayed in relation to other variants of the same product", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    ProductVariant productVariant = (ProductVariant) o;
    return Objects.equals(this.description, productVariant.description) &&
        Objects.equals(this.sequence, productVariant.sequence);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, sequence);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductVariant {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

