package com.englishschool.englishschool.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.englishschool.englishschool.exception.BadRequsetException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class    CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        String token = null;
        if (authHeader != null) {
            token = authHeader.substring("Bearer ".length());
        }
        if (token != null && !token.isEmpty()) {
            try {
                Algorithm algorithm = Algorithm.HMAC256("brouuuchik lox".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                String email = decodedJWT.getSubject();
                String[] role = decodedJWT.getClaim("role").asArray(String.class);
                Collection<SimpleGrantedAuthority> authorities = stream(role).map(x -> new SimpleGrantedAuthority(x))
                        .collect(Collectors.toList());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        email, null, authorities
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } catch (Exception e){
                response.setHeader("error", e.getMessage());
                Map<String, String> error = new HashMap<>();
                error.put("msg", e.getMessage());
                error.put("status code", HttpStatus.BAD_REQUEST.toString());
                response.setContentType(APPLICATION_JSON_VALUE);
                response.sendError(HttpStatus.BAD_REQUEST.value());
                new ObjectMapper().writeValue(response.getOutputStream(), error);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
