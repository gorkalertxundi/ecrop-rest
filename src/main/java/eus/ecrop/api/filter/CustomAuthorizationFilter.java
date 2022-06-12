package eus.ecrop.api.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifier.BaseVerification;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import eus.ecrop.api.domain.User;
import eus.ecrop.api.exception.UserNotFoundException;
import eus.ecrop.api.service.UserService;

/*
* @author Mikel Orobengoa
* @version 19/05/2022
*/

@/**
  * It checks if the request is for the login page or the refresh token page, if
  * it is, it allows the
  * request to go through. If it's not, it checks if the request has a valid JWT
  * token in the header,
  * if it does, it sets the authentication in the security context and allows the
  * request to go
  * through. If it doesn't, it sends a 403 error
  */
Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    Algorithm algorithm;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getServletPath().equals("/login/oauth2/code/google")
                || request.getServletPath().equals("/auth/refresh")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    BaseVerification verification = (BaseVerification) JWT.require(algorithm);
                    JWTVerifier verifier = verification.build();
                    DecodedJWT decodedJWT = verifier.verify(token);

                    String userId = decodedJWT.getSubject();
                    User user = userService.findById(Long.valueOf(userId));
                    if (user == null)
                        throw new UserNotFoundException(Long.valueOf(userId));

                    String[] privileges = decodedJWT.getClaim("privileges").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    if (privileges != null)
                        Arrays.stream(privileges).forEach(p -> authorities.add(new SimpleGrantedAuthority(p)));

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            user, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    e.printStackTrace();
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }

    }

}
