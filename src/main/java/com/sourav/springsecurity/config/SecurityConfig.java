package com.sourav.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.concurrent.TimeUnit;

import static com.sourav.springsecurity.config.ApplicationUserRole.*;

@Configuration
public class SecurityConfig {
    @Autowired
    PasswordConfig passwordConfig;
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable() // else not able to login
                .authorizeRequests()
                .antMatchers("/", "index", "/index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name()) // role based authentication // order of antMatchers in important
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()// browser requests
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/courses", true)
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("hi"); // default to 2 weeks
        return http.build();
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails student = User.builder()
                .username("sourav")
                //.passwordEncoder(s -> String.valueOf(NoOpPasswordEncoder.getInstance()))
                .password(passwordConfig.passwordEncoder().encode("123"))
                //.roles("STUDENT") // ROLE_STUDENT
                //.roles(STUDENT.name())
                .authorities(STUDENT.getGrantedAuthority())
                .build();

        UserDetails admin = User.builder()
                .username("anna")
                //.passwordEncoder(s -> String.valueOf(NoOpPasswordEncoder.getInstance()))
                .password(passwordConfig.passwordEncoder().encode("123"))
                //.roles("ADMIN") // ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthority())
                .build();

        UserDetails adminTrainee = User.builder()
                .username("tommy")
                .password(passwordConfig.passwordEncoder().encode("123"))
                //.roles(ADMINTRAINEE.name()) // ROLE_ADMIN
                .authorities(ADMINTRAINEE.getGrantedAuthority())
                .build();
        return new InMemoryUserDetailsManager(
                student,
                admin,
                adminTrainee
        );
    }


}
