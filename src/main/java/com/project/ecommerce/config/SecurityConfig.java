package com.project.ecommerce.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Protect endpoint /api/orders
        http.authorizeRequests(configurer ->
                        configurer
                                .antMatchers("/api/orders/**")
                                .authenticated())
                .oauth2ResourceServer()
                .jwt();

        // Add CORS Filter
        http.cors();

        // Add content negotiation strategy
        http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());

        // Force a non-empty response body for 401
        Okta.configureResourceServer401ResponseBody(http);

        return http.build();
    }
}
