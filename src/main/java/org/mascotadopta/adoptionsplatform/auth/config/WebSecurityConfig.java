package org.mascotadopta.adoptionsplatform.auth.config;

import org.mascotadopta.adoptionsplatform.auth.filters.JwtTokenVerifierFilter;
import org.mascotadopta.adoptionsplatform.auth.filters.JwtUsernamePasswordAuthenticationFilter;
import org.mascotadopta.adoptionsplatform.users.details.ApplicationUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private final ApplicationUserDetailsService applicationUserDetailsService;
    
    private final JwtUsernamePasswordAuthenticationFilter jwtUsernamePasswordAuthenticationFilter;
    
    private final JwtTokenVerifierFilter jwtTokenVerifierFilter;
    
    public WebSecurityConfig(ApplicationUserDetailsService applicationUserDetailsService,
                             JwtUsernamePasswordAuthenticationFilter jwtUsernamePasswordAuthenticationFilter,
                             JwtTokenVerifierFilter jwtTokenVerifierFilter)
    {
        this.applicationUserDetailsService = applicationUserDetailsService;
        this.jwtUsernamePasswordAuthenticationFilter = jwtUsernamePasswordAuthenticationFilter;
        this.jwtTokenVerifierFilter = jwtTokenVerifierFilter;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(applicationUserDetailsService);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtUsernamePasswordAuthenticationFilter)
                .addFilterAfter(jwtTokenVerifierFilter, JwtUsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                // .antMatchers().permitAll()
                .anyRequest().authenticated();
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }
}
