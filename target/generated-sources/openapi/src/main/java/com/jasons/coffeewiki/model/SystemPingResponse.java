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
 * SystemPingResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-02-03T09:46:22.187758700+02:00[Africa/Johannesburg]", comments = "Generator version: 7.4.0")
public class SystemPingResponse {

  private String message;

  public SystemPingResponse message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Message indicating the service is up and running
   * @return message
  */
  
  @Schema(name = "message", example = "Coffee wiki service is running", description = "Message indicating the service is up and running", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SystemPingResponse systemPingResponse = (SystemPingResponse) o;
    return Objects.equals(this.message, systemPingResponse.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SystemPingResponse {\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

