package com.sourav.springsecurity.auth;

import org.springframework.stereotype.Repository;

import java.util.Optional;
public interface ApplicationUserDao {

    Optional<ApplicationUser> selectApplicationUserByUserName(String username);

}
