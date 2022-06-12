package eus.ecrop.api.security;

import java.util.Arrays;

import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import eus.ecrop.api.filter.CustomAuthorizationFilter;
import eus.ecrop.api.filter.CustomOAuthAuthenticationFilter;

/*
* @author Mikel Orobengoa
* @version 25/05/2022
*/

/**
 * It's a Spring Security configuration class that allows the application to
 * accept requests from the
 * localhost, and it configures the OAuth2 authentication process.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    Algorithm algorithm;

    @Autowired
    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private CustomAuthorizationFilter authorizationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/auth/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new CustomOAuthAuthenticationFilter(clientRegistrationRepository, authorizedClientService,
                        super.authenticationManagerBean(), algorithm))
                .addFilterBefore(authorizationFilter, OAuth2LoginAuthenticationFilter.class)
                .oauth2Login()
                .userInfoEndpoint()
                .oidcUserService(oidcUserService);
    }

    /**
     * Bean that allows to configure CORS headers
     * 
     * @return A CorsConfigurationSource object.
     */
    @Primary
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost"));
        configuration.setAllowedMethods(Arrays.asList("GET, POST, PUT, DELETE, OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With,Origin,Content-Type,Accept,Authorization"));
        configuration.setExposedHeaders(Arrays.asList("X-Requested-With,Origin,Content-Type,Accept,Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    OAuth2UserService<OidcUserRequest, OidcUser> getOidcUserService() {
        return new CustomOidcUserServiceImpl();
    }

}
