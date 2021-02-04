package org.mascotadopta.adoptionsplatform.auth.config;

import org.mascotadopta.adoptionsplatform.auth.JwtService;
import org.mascotadopta.adoptionsplatform.auth.filters.JwtTokenVerifierFilter;
import org.mascotadopta.adoptionsplatform.auth.filters.JwtUsernamePasswordAuthenticationFilter;
import org.mascotadopta.adoptionsplatform.users.details.ApplicationUserDetailsService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * Authentication and authorization configuration.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    /**
     * Custom User details service.
     */
    private final ApplicationUserDetailsService applicationUserDetailsService;
    
    /**
     * JWT authentication filter.
     */
    private final JwtTokenVerifierFilter jwtTokenVerifierFilter;
    
    /**
     * JWT utilities service.
     */
    private final JwtService jwtService;
    
    /**
     * Single constructor.
     *
     * @param applicationUserDetailsService Custom User details service.
     * @param jwtTokenVerifierFilter        JWT authentication filter.
     * @param jwtService                    JWT utilities service.
     */
    public WebSecurityConfig(ApplicationUserDetailsService applicationUserDetailsService,
                             JwtTokenVerifierFilter jwtTokenVerifierFilter,
                             JwtService jwtService)
    {
        this.applicationUserDetailsService = applicationUserDetailsService;
        this.jwtTokenVerifierFilter = jwtTokenVerifierFilter;
        this.jwtService = jwtService;
    }
    
    /**
     * Authentication configuration. Sets a custom UserDetailsService.
     *
     * @param auth Authentication manager builder.
     * @throws Exception If an error occurs when adding the UserDetailsService based authentication.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(applicationUserDetailsService);
    }
    
    /**
     * Authorization configuration.
     *
     * @param http Http security.
     * @throws Exception If an error occurs when setting up a security configuration.
     */
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
                .antMatchers("/adoptions/**", "/pets/my_posts", "/pets/saved").authenticated()
                .antMatchers(HttpMethod.PUT, "/users/**").authenticated()
                .anyRequest().permitAll();
    }
    
    /**
     * Exposes the <code>AuthenticationManager</code> as a Bean.
     *
     * @return The <code>AuthenticationManager</code>.
     * @throws Exception If an error occurs while getting the <code>AuthenticationManager</code>.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }
    
    /**
     * Registers the <code>JwtUsernameAndPasswordAuthenticationFilter</code> in the /login route.
     *
     * @return The <code>FilterRegistrationBean</code> for JwtUsernameAndPasswordAuthenticationFilter.
     * @throws Exception If the AuthenticationManager couldn't be acquired.
     */
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
    
    /**
     * Declares a PasswordEncoder Bean.
     *
     * @return A <code>DelegatingPasswordEncoder</code>.
     */
    @Bean
    public PasswordEncoder getPasswordEncoder()
    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
