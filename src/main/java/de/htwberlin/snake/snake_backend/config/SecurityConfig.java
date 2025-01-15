/*
package de.htwberlin.snake.snake_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;    // <-- Wichtig
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        // 1) CORS erlauben
        .cors(Customizer.withDefaults())

        // 2) CSRF deaktivieren (so wie du es hast)
        .csrf(csrf -> csrf.disable())

        // 3) Freigaben
        .authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/gamemodes/**").permitAll()
        .anyRequest().authenticated()
        )

        // 4) Basic Auth deaktivieren (so wie du es hast)
        .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}


 */