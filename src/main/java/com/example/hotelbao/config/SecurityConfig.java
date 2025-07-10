package com.example.hotelbao.config;

import com.example.hotelbao.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // 1. Endpoints Públicos
                        .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/clientes").hasAnyAuthority("ROLE_CLIENTE", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/quartos/**").permitAll()

                        // 2. Regras Específicas
                        .requestMatchers(HttpMethod.GET, "/clientes/me").authenticated()
                        .requestMatchers(HttpMethod.POST, "/estadias").hasAnyAuthority("ROLE_CLIENTE", "ROLE_ADMIN")
                        .requestMatchers("/relatorios/**").hasAnyAuthority("ROLE_CLIENTE", "ROLE_ADMIN")

                        // 3. Regras de Admin
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/estadias", "/estadias/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/estadias", "/estadias/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/quartos/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/clientes/**").hasAuthority("ROLE_ADMIN")

                        // 4. Qualquer outra requisição é negada
                        .anyRequest().denyAll());

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}