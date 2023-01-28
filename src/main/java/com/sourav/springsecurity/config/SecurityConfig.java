package com.sourav.springsecurity.config;

import com.sourav.springsecurity.auth.ApplicationUserService;
import com.sourav.springsecurity.jwt.JwtUserNameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.sourav.springsecurity.config.ApplicationUserRole.STUDENT;

@Configuration
public class SecurityConfig {
    @Autowired
    PasswordConfig passwordConfig;
    @Autowired
    ApplicationUserService applicationUserService;

    @Autowired
    CustomAuthenticationManager authenticationManager;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable() // else not able to login
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeRequests()
                .antMatchers("/", "index", "/index", "/css/*", "/js/*", "/login").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name()) // role based authentication // order of antMatchers in important
                .anyRequest().authenticated()
                .and()
                    .addFilter(new JwtUserNameAndPasswordAuthenticationFilter(authenticationManager));

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordConfig.passwordEncoder());
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }

}
