package com.programming.techie.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    private final String[] freeResourceUrls = {
            "/swagger-ui.html","/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**",
            "/v2/api-docs/**", "/aggregate/**", "/configuration/ui", "/configuration/security",
            "/webjars/**", "/api-doc/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())  // Enable CORS with defaults or custom config
                .csrf().disable()  // Disable CSRF for stateless APIs
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(freeResourceUrls).permitAll() // Allow open URLs
                                .anyRequest().authenticated()  // Authenticate all other requests
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))  // Use JWT
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));  // Frontend origin
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);  // Apply to all paths
        return source;
    }
}
