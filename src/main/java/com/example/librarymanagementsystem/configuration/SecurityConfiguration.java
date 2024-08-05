package com.example.librarymanagementsystem.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService, JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Autowired
    AuthenticationManagerBuilder auth;

    @Autowired
    DataSource dataSource;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/home/**","/v3/**","/librarymanagementsystem-ui","/librarymanagementsystem-docs","/swagger-ui/**","/librarymanagementsystem-docs/**");
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().authenticated()
                ).authenticationManager(authenticationManager)
                .sessionManagement(x->x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// Set session creation policy
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}