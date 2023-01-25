package com.sourav.springsecurity.auth;

import com.sourav.springsecurity.config.PasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.sourav.springsecurity.config.ApplicationUserRole.*;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

    @Autowired
    PasswordConfig passwordConfig;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUserName(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> applicationUsers = Arrays.asList(
                new ApplicationUser(
                        ADMIN.getGrantedAuthority(),
                        "anna",
                        passwordConfig.passwordEncoder().encode("123"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        STUDENT.getGrantedAuthority(),
                        "sourav",
                        passwordConfig.passwordEncoder().encode("123"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        ADMINTRAINEE.getGrantedAuthority(),
                        "tommy",
                        passwordConfig.passwordEncoder().encode("123"),
                        true,
                        true,
                        true,
                        true
                )
        );
        return applicationUsers;
    }
}
