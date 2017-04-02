package com.chiote.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String LEADER = "ROLE_LEADER";

    public static final String MEMBER = "ROLE_MEMBER";

    private AuthoritiesConstants() {
    }
}
