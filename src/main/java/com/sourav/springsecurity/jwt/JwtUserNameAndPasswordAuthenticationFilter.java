package com.sourav.springsecurity.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sourav.springsecurity.config.CustomAuthenticationManager;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JwtUserNameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    CustomAuthenticationManager authenticationManager;

    public JwtUserNameAndPasswordAuthenticationFilter(CustomAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserNameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper().
                    readValue(request.getInputStream(), UserNameAndPasswordAuthenticationRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );
            Authentication authenticate = authenticationManager.authenticate(authentication);
            return authenticate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String key = ;
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                        .claim("authorities", authResult.getAuthorities())
                                .setExpiration(new Date())
                                        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                                                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                                                        .compact();
        response.addHeader("Authorization", "Bearer " + token);
        chain.doFilter(request, response);

    }

}
