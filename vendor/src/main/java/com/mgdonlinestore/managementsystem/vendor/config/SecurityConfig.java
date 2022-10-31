package com.mgdonlinestore.managementsystem.vendor.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mgdonlinestore.managementsystem.utils.Constants.APP_BASE;

/**
 * @author Khaled ElGohary
 */

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    public interface Jwt2AuthoritiesConverter extends Converter<Jwt, Collection<? extends GrantedAuthority>> {
    }

    @SuppressWarnings("unchecked")
    @Bean
    public Jwt2AuthoritiesConverter authoritiesConverter() {
        // This is a converter for roles as embedded in the JWT by a Keycloak server
        // Roles are taken from both realm_access.roles & resource_access.{client}.roles
        return jwt -> {
//            final var realmAccess = (Map<String, Object>) jwt.getClaims().getOrDefault("realm_access", Map.of());
//            final var realmRoles = (Collection<String>) realmAccess.getOrDefault("roles", List.of());
            final var realmRoles = (Collection<String>) jwt.getClaims().getOrDefault("roles", List.of());

            final var resourceAccess = (Map<String, Object>) jwt.getClaims().getOrDefault("resource_access", Map.of());
            // We assume here you have "spring-addons-confidential" and "spring-addons-public" clients configured with "client roles" mapper in Keycloak
            final var confidentialClientAccess = (Map<String, Object>) resourceAccess.getOrDefault("spring-addons-confidential", Map.of());
            final var confidentialClientRoles = (Collection<String>) confidentialClientAccess.getOrDefault("roles", List.of());
            log.info("roles are : {} ---- {} ",confidentialClientRoles,resourceAccess);
            final var publicClientAccess = (Map<String, Object>) resourceAccess.getOrDefault("spring-addons-public", Map.of());
            final var publicClientRoles = (Collection<String>) publicClientAccess.getOrDefault("roles", List.of());

            return Stream.concat(realmRoles.stream(), Stream.concat(confidentialClientRoles.stream(), publicClientRoles.stream()))
                    .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        };
    }

    public interface Jwt2AuthenticationConverter extends Converter<Jwt, AbstractAuthenticationToken> {
    }

    @Bean
    public Jwt2AuthenticationConverter authenticationConverter(Jwt2AuthoritiesConverter authoritiesConverter) {
        return jwt -> new JwtAuthenticationToken(jwt, authoritiesConverter.convert(jwt));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, Jwt2AuthenticationConverter authenticationConverter, ServerProperties serverProperties)
            throws Exception {

        // Enable OAuth2 with custom authorities mapping
        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(authenticationConverter);

        // Enable anonymous
        http.anonymous();

        // Enable and configure CORS
        http.cors().configurationSource(corsConfigurationSource());

        // State-less session (state in access-token only)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Enable CSRF with cookie repo because of state-less session-management
        http.csrf().csrfTokenRepository(new CookieCsrfTokenRepository());

        // Return 401 (unauthorized) instead of 403 (redirect to login) when authorization is missing or invalid
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Restricted Content\"");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        });

        // If SSL enabled, disable http (https only)
        if (serverProperties.getSsl() != null && serverProperties.getSsl().isEnabled()) {
            http.requiresChannel().anyRequest().requiresSecure();
        } else {
            http.requiresChannel().anyRequest().requiresInsecure();
        }

        // Route security: authenticated to all routes but actuator and Swagger-UI
        // @formatter:off
        http.authorizeRequests()
                .antMatchers("/actuator/health/readiness", "/actuator/health/liveness", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated();
        // @formatter:on

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        // Very permissive CORS config...
        final var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));

        // Limited to API routes (neither actuator nor Swagger-UI)
        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(APP_BASE+"/**", configuration);

        return source;
    }

}
