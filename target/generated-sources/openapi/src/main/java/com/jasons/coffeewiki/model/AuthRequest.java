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
 * AuthRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-02-03T09:41:04.869286200+02:00[Africa/Johannesburg]", comments = "Generator version: 7.4.0")
public class AuthRequest {

  private String username;

  private String password;

  public AuthRequest username(String username) {
    this.username = username;
    return this;
  }

  /**
   * the registered username of the consumer
   * @return username
  */
  @Size(min = 8, max = 15) 
  @Schema(name = "username", example = "johntyrhodes", description = "the registered username of the consumer", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public AuthRequest password(String password) {
    this.password = password;
    return this;
  }

  /**
   * stored password asscociated with the username
   * @return password
  */
  @Size(min = 8, max = 36) 
  @Schema(name = "password", example = "$johntyRhodes#123$", description = "stored password asscociated with the username", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthRequest authRequest = (AuthRequest) o;
    return Objects.equals(this.username, authRequest.username) &&
        Objects.equals(this.password, authRequest.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthRequest {\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append("*").append("\n");
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

