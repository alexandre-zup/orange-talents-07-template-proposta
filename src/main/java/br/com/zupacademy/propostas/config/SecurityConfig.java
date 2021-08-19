package br.com.zupacademy.propostas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String PROPOSTAS = "/api/propostas/**";
    private static final String CARTOES = "/api/cartoes/**";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests -> authorizeRequests
                .antMatchers(HttpMethod.GET, PROPOSTAS).hasAuthority("SCOPE_api-propostas-escopo")
                .antMatchers(HttpMethod.GET, CARTOES).hasAuthority("SCOPE_api-propostas-escopo")
                .antMatchers(HttpMethod.POST, PROPOSTAS).hasAuthority("SCOPE_api-propostas-escopo")
                .anyRequest().authenticated()
        ).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
