package com.sourav.springsecurity.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final JwtSecretKey jwtSecretKey;
    private final UserDetailsService userDetailsService;

    public JwtTokenVerifier(
            JwtConfig jwtConfig,
            JwtSecretKey jwtSecretKey,
            UserDetailsService userDetailsService) {
        this.jwtConfig = jwtConfig;
        this.jwtSecretKey = jwtSecretKey;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
        
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey.getSecretKeyForSigning())
                    .build()
                    .parseClaimsJws(token);

            Claims body = claimsJws.getBody();
            String username = body.getSubject();

            // Validate user exists and is still valid
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                validateUserDetails(userDetails);

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (UsernameNotFoundException e) {
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "User no longer exists");
                return;
            } catch (AuthenticationException e) {
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED, e.getMessage());
                return;
            }

        } catch (JwtException e) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid token");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void validateUserDetails(UserDetails userDetails) {
        if (!userDetails.isEnabled()) {
            throw new DisabledException("User account is disabled");
        }
        if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("User account is locked");
        }
        if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("User account has expired");
        }
        if (!userDetails.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("User credentials have expired");
        }
    }

    private void sendErrorResponse(HttpServletResponse response, 
                                 HttpStatus status, 
                                 String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        error.put("status", status.toString());
        
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}