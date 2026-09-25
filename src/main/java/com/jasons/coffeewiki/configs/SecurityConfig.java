package com.jasons.coffeewiki.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
       http
               .csrf(csrf -> csrf.disable())
               .authorizeHttpRequests(auth -> auth
//                        // Consumers AND admins can GET
                        .requestMatchers(HttpMethod.GET, "/v1/**")
                        .hasAnyAuthority("admins", "consumers")

                        // Only admins can POST
                        .requestMatchers(HttpMethod.POST, "/v1/**")
                        .hasAuthority("admins")

                        // Only admins can PUT
                        .requestMatchers(HttpMethod.PUT, "/v1/**")
                        .hasAuthority("admins")

                        // Only admins can DELETE
                        .requestMatchers(HttpMethod.DELETE, "/v1/**")
                        .hasAuthority("admins")

                        // Everything else requires authentication
                        .anyRequest()
                        .authenticated()
               )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {

            List<String> groups =
                    jwt.getClaimAsStringList("cognito:groups");

            if (groups == null) {
                return List.of();
            }

            return groups.stream()
                    .map(group -> new SimpleGrantedAuthority(group))
                    .collect(Collectors.toList());
        });

        return converter;
    }

}