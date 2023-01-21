package com.sourav.springsecurity.config;

import java.util.*;

import static com.sourav.springsecurity.config.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    STUDENT(Arrays.asList(STUDENT_READ, STUDENT_WRITE)),
    ADMIN(Arrays.asList(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
    ADMINTRAINEE(Arrays.asList(COURSE_READ, STUDENT_READ));

    private final List<ApplicationUserPermission> permissions;

    ApplicationUserRole(List<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }
}
