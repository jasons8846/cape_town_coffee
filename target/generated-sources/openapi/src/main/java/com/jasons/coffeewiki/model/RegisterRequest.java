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
 * RegisterRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-19T16:24:53.944349700+02:00[Africa/Johannesburg]", comments = "Generator version: 7.4.0")
public class RegisterRequest {

  private String name;

  private String username;

  private String password;

  private String roles;

  public RegisterRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * the name describing the consumer
   * @return name
  */
  
  @Schema(name = "name", example = "Johnty", description = "the name describing the consumer", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RegisterRequest username(String username) {
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
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public RegisterRequest password(String password) {
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
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public RegisterRequest roles(String roles) {
    this.roles = roles;
    return this;
  }

  /**
   * List of user roles
   * @return roles
  */
  
  @Schema(name = "roles", example = "ADMIN, HR, USER", description = "List of user roles", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("roles")
  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
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
        Objects.equals(this.username, registerRequest.username) &&
        Objects.equals(this.password, registerRequest.password) &&
        Objects.equals(this.roles, registerRequest.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, username, password, roles);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RegisterRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append("*").append("\n");
    sb.append("    roles: ").append(toIndentedString(roles)).append("\n");
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

