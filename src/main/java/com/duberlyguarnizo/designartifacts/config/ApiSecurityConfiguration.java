package com.duberlyguarnizo.designartifacts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApiSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/content/**", "/api/graph/**", "/api/link/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/api/admin/**", "/api/visit/**").hasRole("API USER")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User
                .withUsername("apiUser")
                .password("{noop}password")
                .roles("API USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
