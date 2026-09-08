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
 * AuthRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-08T09:50:26.188626300+02:00[Africa/Johannesburg]", comments = "Generator version: 7.25.0")
public class AuthRequest {

  private @Nullable String username;

  private @Nullable String password;

  public AuthRequest username(@Nullable String username) {
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
  public @Nullable String getUsername() {
    return username;
  }

  @JsonProperty("username")
  public void setUsername(@Nullable String username) {
    this.username = username;
  }

  public AuthRequest password(@Nullable String password) {
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
  public @Nullable String getPassword() {
    return password;
  }

  @JsonProperty("password")
  public void setPassword(@Nullable String password) {
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
  private String toIndentedString(@Nullable Object o) {
    return o == null ? "null" : o.toString().replace("\n", "\n    ");
  }
}

