package com.sourav.springsecurity.auth;

import java.util.Optional;

public class FakeApplicationUserDaoService implements ApplicationUserDao {
    @Override
    public Optional<ApplicationUser> selectApplicationUserByUserName(String username) {
        return Optional.empty();
    }
}
