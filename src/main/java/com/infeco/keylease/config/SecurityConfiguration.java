package com.infeco.keylease.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

    @Bean
    //Autorise toutes les requêtes en terme d'authentification
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authz -> authz
                .requestMatchers("/**").permitAll()
                .anyRequest().permitAll()
        )
                .httpBasic(withDefaults())
                .csrf().disable();
        httpSecurity.headers().frameOptions().sameOrigin();
        return httpSecurity.build();
    }
}
