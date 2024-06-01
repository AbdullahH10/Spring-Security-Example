package com.abdullah.SpringSecurityExample.filter;

import com.abdullah.SpringSecurityExample.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("authorization");

        if(authorizationHeader != null && authorizationHeader.contains("Bearer")){
            String token = authorizationHeader.split(" ")[1].trim();
            if(jwtUtil.isValid(token)){
                String email = jwtUtil.getEmail(token);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        new ArrayList<>()
                );
                WebAuthenticationDetails authDetails = new WebAuthenticationDetailsSource().buildDetails(request);
                authToken.setDetails(authDetails);
                SecurityContextHolder.getContext().setAuthentication(authToken);
                filterChain.doFilter(request,response);
            }
            filterChain.doFilter(request,response);
        }
        filterChain.doFilter(request,response);
    }
}
