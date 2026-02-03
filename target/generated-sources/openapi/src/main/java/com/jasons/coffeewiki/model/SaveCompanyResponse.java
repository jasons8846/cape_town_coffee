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
 * SaveCompanyResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-02-03T09:41:04.869286200+02:00[Africa/Johannesburg]", comments = "Generator version: 7.4.0")
public class SaveCompanyResponse {

  private String message;

  private String companyCode;

  public SaveCompanyResponse message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Indicates that the company details was saved
   * @return message
  */
  
  @Schema(name = "message", example = "Company saved Successfully!", description = "Indicates that the company details was saved", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public SaveCompanyResponse companyCode(String companyCode) {
    this.companyCode = companyCode;
    return this;
  }

  /**
   * unique code asscociated to each company on creation
   * @return companyCode
  */
  
  @Schema(name = "companyCode", example = "12weHIaq3ATOP1nM3Cx", description = "unique code asscociated to each company on creation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("companyCode")
  public String getCompanyCode() {
    return companyCode;
  }

  public void setCompanyCode(String companyCode) {
    this.companyCode = companyCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SaveCompanyResponse saveCompanyResponse = (SaveCompanyResponse) o;
    return Objects.equals(this.message, saveCompanyResponse.message) &&
        Objects.equals(this.companyCode, saveCompanyResponse.companyCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, companyCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SaveCompanyResponse {\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    companyCode: ").append(toIndentedString(companyCode)).append("\n");
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

