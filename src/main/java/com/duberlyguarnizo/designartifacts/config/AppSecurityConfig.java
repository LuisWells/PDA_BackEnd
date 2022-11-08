package com.duberlyguarnizo.designartifacts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();
        http.authorizeRequests()
                .mvcMatchers("/"
                        , "index"
                        , "/css/**"
                        , "/js/*"
                        , "/images/**"
                        , "/webfonts/*"
                        , "api/content/*"
                        , "api/graph/*"
                        , "api/link/*"
                        , "/login*"
                        , "/comentarios*"
                        , "/error").permitAll()
                .anyRequest().authenticated();
        http.formLogin()
                .permitAll()
                .loginPage("/login");
        http.logout()
                .permitAll();


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
