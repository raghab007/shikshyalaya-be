package com.e_learning.Sikshyalaya.config;

import com.e_learning.Sikshyalaya.service.JWTService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    //    UsernamePasswordAuthenticationToken
    private final JWTService jwtService;

    public JwtFilter(UserDetailsService userDetailsService, JWTService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            System.out.println("authorization header");
            System.out.println(authorizationHeader);
            if (authorizationHeader == null || authorizationHeader.isEmpty()) {
                chain.doFilter(request, response);
                return;
            } else {
                String username = null;
                String jwt = null;
                if (authorizationHeader.startsWith("Bearer ")) {
                    jwt = authorizationHeader.substring(7);
                    username = jwtService.extractUserName(jwt);
                    System.out.println(username);
                    if (username == null) {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        return;
                    }
                }
                if (username != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtService.validateToken(jwt)) {
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        chain.doFilter(request, response);
                    } else {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    }
                } else {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error("Exception: {}", String.valueOf(e));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}