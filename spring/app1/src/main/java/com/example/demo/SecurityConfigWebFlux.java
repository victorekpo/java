//package com.example.demo;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//@ConditionalOnProperty(name = "enableWebFlux", havingValue = "true")
//public class SecurityConfigWebFlux {
//
//    private final CustomAuthHandlerWebFlux successHandler;
//
//    public SecurityConfigWebFlux(CustomAuthHandlerWebFlux successHandler) {
//        this.successHandler = successHandler;
//    }
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        http
//                .authorizeExchange(authorize -> authorize
//                        .pathMatchers("/swagger-ui.html", "/login").permitAll()
//                        .anyExchange().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .authenticationSuccessHandler(successHandler)
//                )
//                .logout(logout -> logout.logoutUrl("/logout"));
//        return http.build();
//    }
//}