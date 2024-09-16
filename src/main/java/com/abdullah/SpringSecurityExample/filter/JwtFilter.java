package com.abdullah.SpringSecurityExample.filter;

import com.abdullah.SpringSecurityExample.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
                String authorities = jwtUtil.getAuthorities(token);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        getAuthorities(authorities)
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

    private ArrayList<SimpleGrantedAuthority> getAuthorities(String authorities){
        try{
            authorities = authorities.substring(1,authorities.length()-1);
            String[] splitAuthorities = authorities.split(",");
            ArrayList<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>(splitAuthorities.length);
            for(int i=splitAuthorities.length-1;i>=0;i--){
                grantedAuthorities.add(new SimpleGrantedAuthority(splitAuthorities[i]));
            }
            return grantedAuthorities;
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
