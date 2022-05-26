package eus.ecrop.api.filter;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;

import eus.ecrop.api.domain.Privilege;
import eus.ecrop.api.domain.User;
import eus.ecrop.api.security.CustomOidcUser;

/*
* @author Mikel Orobengoa
* @version 26/05/2022
*/

/**
 * A filter that generates a JWT token and a refresh token after a successful
 * authentication
 */
public class CustomOAuthAuthenticationFilter extends OAuth2LoginAuthenticationFilter {

    private Algorithm algorithm;

    @Autowired
    public CustomOAuthAuthenticationFilter(ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService,
            AuthenticationManager authenticationManager, Algorithm algorithm) {
        super(clientRegistrationRepository, authorizedClientService);
        super.setAuthenticationManager(authenticationManager);
        this.algorithm = algorithm;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        CustomOidcUser oidcUser = (CustomOidcUser) authResult.getPrincipal();
        User user = oidcUser.getUser();

        Date accessTokenExpiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60);

        String accessToken = JWT.create()
                .withSubject(user.getId().toString())
                .withExpiresAt(accessTokenExpiration)
                .withIssuer(request.getRequestURL().toString())
                .withClaim("privileges",
                        user.getRole().getPrivileges().stream().map(Privilege::getCode)
                                .collect(Collectors.toList()))
                .sign(algorithm);

        response.addHeader("Set-Cookie",
                "access_token=" + accessToken + "; expires=" + accessTokenExpiration + "; Path=/;");

        Date refreshTokenExpiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);

        String refreshToken = JWT.create()
                .withSubject(user.getId().toString())
                .withExpiresAt(refreshTokenExpiration)
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        response.addHeader("Set-Cookie",
                "refresh_token=" + refreshToken + "; expires=" + refreshTokenExpiration + "; Path=/;");
        response.addHeader("Set-Cookie", "JSESSIONID=" + "; Path=/;");
        response.setHeader("access_token", accessToken);
        response.setHeader("refresh_token", refreshToken);
        response.sendRedirect(request.getScheme() + "://" + request.getServerName());

    }

}
