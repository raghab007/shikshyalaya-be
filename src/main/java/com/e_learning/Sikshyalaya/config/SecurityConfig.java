package com.e_learning.Sikshyalaya.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors()
                .and()
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/login", "/", "signup", "/courses", "/course/**", "/upload_video", "/testAPI",
                                "/course", "/raghab", "/messages/**")
                        .permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/files/**").permitAll()
                        .requestMatchers("/videos/**").permitAll()
                        .requestMatchers("/course/videos/**").permitAll()
                        .requestMatchers("/comment/**").authenticated()
                        .requestMatchers("/instructor/**").hasRole("INSTRUCTOR")
                        .requestMatchers("/demo").hasRole("INSTRUCTOR")
                        .requestMatchers("/enrollment/**").permitAll()
                        .requestMatchers("/check").permitAll()
                        // .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/courses/**").permitAll()
                        .requestMatchers("/user").authenticated()
                        .requestMatchers("/payment").permitAll()
                        .requestMatchers("/Users/**").permitAll()
                        .requestMatchers("/api/rating-reviews/course/**").permitAll()
                        .requestMatchers("/api/rating-reviews").authenticated()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
