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
public class JwtFilter  extends OncePerRequestFilter{

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
          String [] strings =    request.getRequestURI().split("/");
          String URL = request.getRequestURI();
          if(request.getRequestURI().equals("/login") || request.getRequestURI().equals("/signup")|| request.getRequestURI().equals("/messages")||
                  request.getRequestURI().equals("/courses") ||strings[1].equals("course")||
                  strings[1].equals("messages")||
                  request.getRequestURI().equals("/upload_video")||
                  request.getRequestURI().equals("/testAPI") || request.getRequestURI().equals("/course")||
                  request.getRequestURI().equals("/raghab")||
                  request.getRequestURI().equals("/")||strings[1].equals("images")||request.getRequestURI().equals("/payment")
          || strings[1].equals("videos")) {
              chain.doFilter(request, response);
              return;
          }
          String authorizationHeader = request.getHeader("Authorization");
          if (authorizationHeader==null){
              response.setStatus(HttpStatus.UNAUTHORIZED.value());
              return;
          }
          String username = null;
          String jwt = null;
           if (authorizationHeader.startsWith("Bearer ")) {jwt = authorizationHeader.substring(7);
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
               }else{
                   response.setStatus(HttpStatus.UNAUTHORIZED.value());
               }
           }else{
               response.setStatus(HttpStatus.UNAUTHORIZED.value());
           }
           //chain.doFilter(request, response);
       }catch (Exception e){
            System.out.println(e.getMessage());
            log.error("Exception: {}", String.valueOf(e));
          response.setStatus(HttpStatus.UNAUTHORIZED.value());
       }
    }
}