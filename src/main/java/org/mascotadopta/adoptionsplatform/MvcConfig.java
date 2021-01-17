package org.mascotadopta.adoptionsplatform;

import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC configuration.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer
{
    /**
     * Sets the configuration necessary to add the SameSite cookie attribute with Strict as its value to every processed
     * outgoing cookie.
     *
     * @return A TomcatContextCustomizer.
     */
    @Bean
    public TomcatContextCustomizer sameSiteCookiesConfig()
    {
        return context -> {
            final Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
            cookieProcessor.setSameSiteCookies(SameSiteCookies.STRICT.getValue());
            context.setCookieProcessor(cookieProcessor);
        };
    }
}
