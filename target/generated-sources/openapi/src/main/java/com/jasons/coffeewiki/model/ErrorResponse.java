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
 * ErrorResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-05T19:04:16.429649900+02:00[Africa/Johannesburg]", comments = "Generator version: 7.4.0")
public class ErrorResponse {

  private String message;

  private String details;

  public ErrorResponse message(String message) {
    this.message = message;
    return this;
  }

  /**
   * field describing the error
   * @return message
  */
  
  @Schema(name = "message", example = "No data available", description = "field describing the error", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ErrorResponse details(String details) {
    this.details = details;
    return this;
  }

  /**
   * details giving more information on the error
   * @return details
  */
  
  @Schema(name = "details", example = "No company data is available for the request", description = "details giving more information on the error", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("details")
  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse errorResponse = (ErrorResponse) o;
    return Objects.equals(this.message, errorResponse.message) &&
        Objects.equals(this.details, errorResponse.details);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, details);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    details: ").append(toIndentedString(details)).append("\n");
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

