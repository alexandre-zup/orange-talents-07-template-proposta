package br.com.zupacademy.propostas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String PROPOSTAS = "/api/propostas/**";
    private static final String CARTOES = "/api/cartoes/**";
    private static final String ACTUATOR= "/actuator/**";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests(authorizeRequests -> authorizeRequests
                .antMatchers(HttpMethod.GET, PROPOSTAS).hasAuthority("SCOPE_api-propostas-escopo")
                .antMatchers(HttpMethod.GET, CARTOES).hasAuthority("SCOPE_api-propostas-escopo")
                .antMatchers(HttpMethod.POST, PROPOSTAS).hasAuthority("SCOPE_api-propostas-escopo")
                .antMatchers(HttpMethod.GET, ACTUATOR).permitAll()
                .anyRequest().authenticated()
        ).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
