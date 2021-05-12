package org.mascotadopta.adoptionplatform.auth;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;

@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter
{
    private final UserRegistrationFilter userRegistrationFilter;
    
    public ResourceServerConfig(UserRegistrationFilter userRegistrationFilter)
    {
        this.userRegistrationFilter = userRegistrationFilter;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.cors().and()
                .authorizeRequests()
                .antMatchers("/adoptions/my_applications").authenticated().and()
                .addFilterAfter(userRegistrationFilter, BearerTokenAuthenticationFilter.class)
                .oauth2ResourceServer()
                .jwt();
    }
    
}
