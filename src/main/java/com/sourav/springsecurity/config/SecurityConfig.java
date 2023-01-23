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
import static com.sourav.springsecurity.config.ApplicationUserRole.*;

@Configuration
public class SecurityConfig {
    @Autowired
    PasswordConfig passwordConfig;
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                //.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").authenticated()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name()) // role based authentication // order of antMatchers in important
//                .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.name())
//                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE .name())
//                .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.name())
//                .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMINTRAINEE.name(), ADMIN.name(), STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()// browser requests
                .and()
                .httpBasic();// backend services .... basic auth
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
