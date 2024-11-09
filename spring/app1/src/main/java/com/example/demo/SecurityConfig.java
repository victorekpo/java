package com.example.demo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "enableWebFlux", havingValue = "false", matchIfMissing = true)
public class SecurityConfig {

    private final CustomAuthHandler successHandler;

    public SecurityConfig(CustomAuthHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(form -> form
//                        .loginPage("/login")
                        .successHandler(successHandler)
                        .permitAll()
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui.html", "/login", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }
}

// The term "Security Filter Chain" refers to the sequence of security filters that are applied to incoming HTTP requests in a Spring Security application. These filters are responsible for various security-related tasks such as authentication, authorization, and session management.  In the context of Spring Security, the SecurityFilterChain is a part of the configuration that defines how security is applied to web requests. It is indeed a part of the Spring Web MVC framework when used in traditional, servlet-based applications.  Here is a brief explanation of the SecurityFilterChain:
// Security Filters: These are components that intercept HTTP requests and perform security checks.
// Chain of Responsibility: The filters are organized in a chain, where each filter has a specific responsibility and can pass the request to the next filter in the chain.
//        Configuration: The SecurityFilterChain bean in Spring Security configures this chain, specifying which filters to apply and in what order.