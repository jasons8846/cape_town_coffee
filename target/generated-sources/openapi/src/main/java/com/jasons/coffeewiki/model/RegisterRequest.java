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
 * RegisterRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-10-04T07:49:29.183975400+02:00[Africa/Johannesburg]", comments = "Generator version: 7.25.0")
public class RegisterRequest {

  private @Nullable String name;

  private @Nullable String email;

  private @Nullable String username;

  private @Nullable String password;

  public RegisterRequest name(@Nullable String name) {
    this.name = name;
    return this;
  }

  /**
   * the name describing the consumer
   * @return name
   */
  
  @Schema(name = "name", example = "Johnty", description = "the name describing the consumer", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public @Nullable String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(@Nullable String name) {
    this.name = name;
  }

  public RegisterRequest email(@Nullable String email) {
    this.email = email;
    return this;
  }

  /**
   * the email of the consumer
   * @return email
   */
  @jakarta.validation.constraints.Email 
  @Schema(name = "email", example = "johnty@example.com", description = "the email of the consumer", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public @Nullable String getEmail() {
    return email;
  }

  @JsonProperty("email")
  public void setEmail(@Nullable String email) {
    this.email = email;
  }

  public RegisterRequest username(@Nullable String username) {
    this.username = username;
    return this;
  }

  /**
   * the username of the consumer
   * @return username
   */
  @Size(min = 8, max = 15) 
  @Schema(name = "username", example = "johntyrhodes", description = "the username of the consumer", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("username")
  public @Nullable String getUsername() {
    return username;
  }

  @JsonProperty("username")
  public void setUsername(@Nullable String username) {
    this.username = username;
  }

  public RegisterRequest password(@Nullable String password) {
    this.password = password;
    return this;
  }

  /**
   * password asscociated with the username
   * @return password
   */
  @Size(min = 8, max = 36) 
  @Schema(name = "password", example = "$johntyRhodes#123$", description = "password asscociated with the username", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    RegisterRequest registerRequest = (RegisterRequest) o;
    return Objects.equals(this.name, registerRequest.name) &&
        Objects.equals(this.email, registerRequest.email) &&
        Objects.equals(this.username, registerRequest.username) &&
        Objects.equals(this.password, registerRequest.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, email, username, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RegisterRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
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

