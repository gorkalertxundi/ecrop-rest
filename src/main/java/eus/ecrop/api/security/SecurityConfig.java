package eus.ecrop.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

/*
* @author Mikel Orobengoa
* @version 06/05/2022
*/

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception
    // {
    // auth.userDetailsService(userDetailsService);
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
        .and()
        .oauth2Login().userInfoEndpoint().oidcUserService(oidcUserService);
    }

    @Bean
    OAuth2UserService<OidcUserRequest, OidcUser> getOidcUserService() {
        return new CustomOidcUserServiceImpl();
    }

}
