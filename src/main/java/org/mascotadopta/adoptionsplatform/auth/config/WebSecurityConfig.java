package org.mascotadopta.adoptionsplatform.auth.config;

import org.mascotadopta.adoptionsplatform.auth.JwtService;
import org.mascotadopta.adoptionsplatform.auth.filters.JwtTokenVerifierFilter;
import org.mascotadopta.adoptionsplatform.auth.filters.JwtUsernamePasswordAuthenticationFilter;
import org.mascotadopta.adoptionsplatform.users.details.ApplicationUserDetailsService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private final ApplicationUserDetailsService applicationUserDetailsService;
    
    private final JwtTokenVerifierFilter jwtTokenVerifierFilter;
    
    private final JwtService jwtService;
    
    public WebSecurityConfig(ApplicationUserDetailsService applicationUserDetailsService,
                             JwtTokenVerifierFilter jwtTokenVerifierFilter,
                             JwtService jwtService)
    {
        this.applicationUserDetailsService = applicationUserDetailsService;
        this.jwtTokenVerifierFilter = jwtTokenVerifierFilter;
        this.jwtService = jwtService;
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
                .addFilterAfter(jwtTokenVerifierFilter, LogoutFilter.class)
                .authorizeRequests()
                .antMatchers("/adoptions/**").authenticated()
                .anyRequest().permitAll();
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public FilterRegistrationBean<JwtUsernamePasswordAuthenticationFilter> jwtUsernamePasswordAuthenticationFilter() throws
            Exception
    {
        FilterRegistrationBean<JwtUsernamePasswordAuthenticationFilter> registrationBean
                = new FilterRegistrationBean<>();
        
        registrationBean.setFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtService));
        registrationBean.addUrlPatterns("/login");
        
        return registrationBean;
    }
}
