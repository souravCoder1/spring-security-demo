package com.sourav.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.sourav.springsecurity.config.ApplicationUserRole.ADMIN;
import static com.sourav.springsecurity.config.ApplicationUserRole.STUDENT;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(authorize -> authorize
                .antMatchers("/", "/index", "/index.html").permitAll()
                .antMatchers("/api/v1/students/**").hasAnyRole(STUDENT.name(), ADMIN.name())
                .anyRequest().authenticated()
            )
            .formLogin(withDefaults())
            .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails student = User.builder()
            .username("sourav")
            .password(passwordEncoder.encode("123"))
            .roles(STUDENT.name())
            .authorities(STUDENT.getGrantedAuthorities())
            .build();

        UserDetails admin = User.builder()
            .username("anna")
            .password(passwordEncoder.encode("123"))
            .roles(ADMIN.name())
            .authorities(ADMIN.getGrantedAuthorities())
            .build();

        return new InMemoryUserDetailsManager(student, admin);
    }
}
