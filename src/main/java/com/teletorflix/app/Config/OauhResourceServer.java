package com.teletorflix.app.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class OauhResourceServer extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "resource_id";

    @Autowired
    private RestAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                anonymous().disable()
                .authorizeRequests().anyRequest().authenticated()
                .antMatchers("/users/**").access("hasRole('ADMIN')")
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint);
    }

}
