package org.mascotadopta.adoptionsplatform.auth;

import java.util.concurrent.TimeUnit;

public class AuthConstants
{
    public static final long ACCESS_TOKEN_DURATION_MS = TimeUnit.MINUTES.toMillis(10);
    
    public static final long REFRESH_TOKEN_DURATION_MS = TimeUnit.DAYS.toMillis(14);
    
    public static final String TOKEN_PREFIX = "Bearer ";
    
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    
    public static final String SAME_SITE = "; SameSite=strict";
}
