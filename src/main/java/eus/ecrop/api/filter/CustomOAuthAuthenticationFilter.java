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
import org.springframework.beans.factory.annotation.Value;
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
* @version 19/05/2022
*/

public class CustomOAuthAuthenticationFilter extends OAuth2LoginAuthenticationFilter {

    @Value("${spring.security.jwt.secret}")
    private String jwtSecret;

    @Autowired
    public CustomOAuthAuthenticationFilter(ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService,
            AuthenticationManager authenticationManager) {
        super(clientRegistrationRepository, authorizedClientService);
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        CustomOidcUser oidcUser = (CustomOidcUser) authResult.getPrincipal();
        User user = oidcUser.getUser();

        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
        String accessToken = JWT.create()
                .withSubject(user.getId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 10 * 60)))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("privileges",
                        user.getRole().getPrivileges().stream().map(Privilege::getCode)
                                .collect(Collectors.toList()))
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        response.addHeader("Set-Cookie", "access_token=" + accessToken + "; expires="
                + new Date(System.currentTimeMillis() + (1000 * 10 * 60)) + "; Path=/;");
        response.addHeader("Set-Cookie",
                "refresh_token=" + refreshToken + "; expires="
                        + new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30))
                        + "; Path=/;");
        response.addHeader("Set-Cookie", "JSESSIONID=" + "; Path=/;");
        response.setHeader("access_token", accessToken);
        response.setHeader("refresh_token", refreshToken);
        response.sendRedirect(request.getScheme() + "://" + request.getServerName());

    }

}
