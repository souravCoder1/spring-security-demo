package com.sourav.springsecurity.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;

import static com.sourav.springsecurity.config.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    DataSource dataSource;
    @Autowired
    PasswordConfig passwordConfig;
    // Enable jdbc authentication
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.
                authorizeRequests()
                .antMatchers("/**").authenticated()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name()) // role based authentication
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()// browser requests
                .and()
                .httpBasic();// backend services
        return http.build();
    }

//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails student = User.builder()
//                .username("sourav")
//                //.passwordEncoder(s -> String.valueOf(NoOpPasswordEncoder.getInstance()))
//                .password(passwordConfig.passwordEncoder().encode("123"))
//                //.roles("STUDENT") // ROLE_STUDENT
//                .roles(STUDENT.name())
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("anna")
//                //.passwordEncoder(s -> String.valueOf(NoOpPasswordEncoder.getInstance()))
//                .password(passwordConfig.passwordEncoder().encode("123"))
//                .roles("ADMIN") // ROLE_ADMIN
//                .build();
//        return new InMemoryUserDetailsManager(
//                student,
//                admin
//        );
//    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

}
