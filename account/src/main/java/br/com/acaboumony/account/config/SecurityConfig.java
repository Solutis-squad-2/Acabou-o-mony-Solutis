package br.com.acaboumony.account.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private FilterToken filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        // Libera os endpoints do Swagger
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/account/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/account/confirmar-codigo").permitAll()
                        .requestMatchers(HttpMethod.POST, "/account/register").permitAll()
                        .requestMatchers("/account/swagger-ui/**", "/account/v3/api-docs/**", "/account/swagger-resources/**", "/account/webjars/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/account/account/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/account/account/confirmar-codigo").permitAll()
                        .requestMatchers(HttpMethod.POST, "/account/account/register").permitAll()
                        .requestMatchers("/account").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
