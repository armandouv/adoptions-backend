package org.mascotadopta.adoptionplatform.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
    
    @Bean
    CorsConfigurationSource corsConfigurationSource()
    {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
