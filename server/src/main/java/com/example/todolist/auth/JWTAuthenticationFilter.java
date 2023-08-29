package com.example.todolist.auth;

import com.example.todolist.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@AllArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    JWTUtils jwtUtils;
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JWT filter filtering");

        String jwtToken = getJWTToken(request.getCookies());
        if (jwtToken != null){
            if(jwtUtils.verifyToken(jwtToken)){
                String username = jwtUtils.getSubject(jwtToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getJWTToken (Cookie[] cookies){
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        return token;
    }
}
