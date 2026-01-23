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
 * AuthResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-23T11:28:51.247156800+02:00[Africa/Johannesburg]", comments = "Generator version: 7.4.0")
public class AuthResponse {

  private String bearer;

  public AuthResponse bearer(String bearer) {
    this.bearer = bearer;
    return this;
  }

  /**
   * bearer token in the form of a JWT
   * @return bearer
  */
  
  @Schema(name = "bearer", example = "QQ7nf3Ij83qr1AgTw9hkO", description = "bearer token in the form of a JWT", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("bearer")
  public String getBearer() {
    return bearer;
  }

  public void setBearer(String bearer) {
    this.bearer = bearer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthResponse authResponse = (AuthResponse) o;
    return Objects.equals(this.bearer, authResponse.bearer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bearer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthResponse {\n");
    sb.append("    bearer: ").append(toIndentedString(bearer)).append("\n");
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

