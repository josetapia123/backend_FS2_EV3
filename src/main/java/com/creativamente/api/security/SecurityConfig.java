package com.creativamente.api.security;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// Configura CORS, sesión stateless con JWT y reglas de acceso por rol
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/proyectos/**")
                        .hasAnyRole("ADMIN", "DIRECTOR", "CLIENTE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/proyectos/**")
                        .hasAnyRole("ADMIN", "DIRECTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/proyectos/**")
                        .hasAnyRole("ADMIN", "DIRECTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/proyectos/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/tareas/**")
                        .hasAnyRole("ADMIN", "DIRECTOR", "ESPECIALISTA")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/tareas/**")
                        .hasAnyRole("ADMIN", "DIRECTOR", "ESPECIALISTA")
                        .requestMatchers("/api/v1/tareas/**")
                        .hasAnyRole("ADMIN", "DIRECTOR")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // Origen del frontend Vite en desarrollo y del API Gateway
        config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:8080"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}