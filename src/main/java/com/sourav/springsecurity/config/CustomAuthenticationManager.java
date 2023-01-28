package com.sourav.springsecurity.config;

import com.sourav.springsecurity.auth.ApplicationUser;
import com.sourav.springsecurity.auth.FakeApplicationUserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    FakeApplicationUserDaoService user;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        List<ApplicationUser> applicationUsers = user.getApplicationUsers();
        String password = applicationUsers.get(0).getPassword();
        if(!password.equals(authentication.getCredentials().toString())) {
            System.out.println(authentication.getCredentials());
            throw new BadCredentialsException("Wrong Pasword");
        }
        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal().toString(), authentication.getCredentials().toString());
    }
}
