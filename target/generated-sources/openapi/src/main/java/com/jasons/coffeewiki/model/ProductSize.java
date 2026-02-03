package com.jasons.coffeewiki.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ProductSize
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-02-03T09:41:04.869286200+02:00[Africa/Johannesburg]", comments = "Generator version: 7.4.0")
public class ProductSize {

  private String description;

  private Integer sequence;

  public ProductSize description(String description) {
    this.description = description;
    return this;
  }

  /**
   * describes the cup size of the product
   * @return description
  */
  
  @Schema(name = "description", example = "SMALL", description = "describes the cup size of the product", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ProductSize sequence(Integer sequence) {
    this.sequence = sequence;
    return this;
  }

  /**
   * the varaint sequence as a guide as to where in a list it should be displayed in relation to other variants of the same product
   * @return sequence
  */
  
  @Schema(name = "sequence", example = "1", description = "the varaint sequence as a guide as to where in a list it should be displayed in relation to other variants of the same product", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    ProductSize productSize = (ProductSize) o;
    return Objects.equals(this.description, productSize.description) &&
        Objects.equals(this.sequence, productSize.sequence);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, sequence);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductSize {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

