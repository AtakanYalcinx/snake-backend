package de.htwberlin.snake.snake_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF-Schutz deaktivieren
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/gamemodes/**").permitAll() // Endpunkte öffentlich machen
                        .anyRequest().authenticated() // Alle anderen Endpunkte erfordern Authentifizierung
                )
                .httpBasic(httpBasic -> httpBasic.disable()); // Basic Authentication deaktivieren
        return http.build();
    }
}
