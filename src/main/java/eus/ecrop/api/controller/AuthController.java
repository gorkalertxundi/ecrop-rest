package eus.ecrop.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eus.ecrop.api.domain.Privilege;
import eus.ecrop.api.domain.User;
import eus.ecrop.api.exception.MissingTokenException;
import eus.ecrop.api.service.UserService;

@RestController
@RequestMapping(path = "/auth", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * It takes the refresh token from the request header, verifies it, and then
     * creates a new access token
     * with the same subject and claims as the refresh token
     * 
     * @param request  The request object
     * @param response The response object that will be sent back to the client.
     * @return A map of tokens.
     */
    @GetMapping("/refresh")
    @CrossOrigin(origins = "*")
    public Map<String, String> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String userId = decodedJWT.getSubject();
                User user = userService.findById(Long.valueOf(userId));
                String accessToken = JWT.create()
                        .withSubject(user.getId().toString())
                        .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 10 * 60)))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("privileges",
                                user.getRole().getPrivileges().stream().map(Privilege::getCode)
                                        .collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                return tokens;
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
        } else {
            throw new MissingTokenException("Refresh token is missing");
        }
        return new HashMap<>();
    }

}
