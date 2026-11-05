package com.sourav.springsecurity.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sourav.springsecurity.config.ApplicationUserPermission.COURSE_READ;
import static com.sourav.springsecurity.config.ApplicationUserPermission.COURSE_WRITE;
import static com.sourav.springsecurity.config.ApplicationUserPermission.STUDENT_READ;
import static com.sourav.springsecurity.config.ApplicationUserPermission.STUDENT_WRITE;

public enum ApplicationUserRole {
    STUDENT(Arrays.asList(STUDENT_READ, STUDENT_WRITE)),
    ADMIN(Arrays.asList(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE));

    private final List<ApplicationUserPermission> permissions;

    ApplicationUserRole(List<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public List<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getGrantedAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (ApplicationUserPermission permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
        }

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
