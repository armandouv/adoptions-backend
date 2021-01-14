package org.mascotadopta.adoptionsplatform.auth;

import java.util.concurrent.TimeUnit;

/**
 * Constant values used throughout Auth package.
 */
public class AuthConstants
{
    /**
     * The lifetime of an access token in milliseconds.
     */
    public static final long ACCESS_TOKEN_DURATION_MS = TimeUnit.MINUTES.toMillis(10);
    
    /**
     * The lifetime of a refresh token in milliseconds.
     */
    public static final long REFRESH_TOKEN_DURATION_MS = TimeUnit.DAYS.toMillis(14);
    
    /**
     * Token prefix to add in Authorization header.
     */
    public static final String TOKEN_PREFIX = "Bearer ";
    
    /**
     * Name of the cookie that will contain the refresh token.
     */
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    
    /**
     * Manually-crafted string to add the SameSite header, since it isn't supported natively.
     */
    public static final String SAME_SITE = "; SameSite=strict";
}
