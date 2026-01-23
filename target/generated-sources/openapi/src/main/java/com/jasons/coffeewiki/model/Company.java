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
 * Company
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-23T13:34:04.987765500+02:00[Africa/Johannesburg]", comments = "Generator version: 7.4.0")
public class Company {

  private String code;

  private String name;

  public Company code(String code) {
    this.code = code;
    return this;
  }

  /**
   * unique code asscociated to each company on creation
   * @return code
  */
  
  @Schema(name = "code", example = "12weHIaq3ATOP1nM3Cx", description = "unique code asscociated to each company on creation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Company name(String name) {
    this.name = name;
    return this;
  }

  /**
   * the name of the company
   * @return name
  */
  
  @Schema(name = "name", example = "Starbucks", description = "the name of the company", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Company company = (Company) o;
    return Objects.equals(this.code, company.code) &&
        Objects.equals(this.name, company.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Company {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

