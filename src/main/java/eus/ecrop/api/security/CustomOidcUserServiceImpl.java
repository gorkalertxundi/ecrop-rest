package eus.ecrop.api.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import eus.ecrop.api.domain.User;
import eus.ecrop.api.service.UserService;

/*
* @author Mikel Orobengoa
* @version 10/05/2022
*/
@Component
public class CustomOidcUserServiceImpl implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private OidcUserService oidcUserService = new OidcUserService();

    @Autowired
    private UserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = oidcUserService.loadUser(userRequest);
        User user = userService.findByIdToken(oidcUser.getSubject());
        if (user == null) {
            user = userService.registerUser(oidcUser.getSubject());
        }

        Set<SimpleGrantedAuthority> authorities = user.getRole().getPrivileges().stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.getCode())).collect(Collectors.toSet());
        oidcUser = new CustomOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), userRequest.getAccessToken());
        return oidcUser;
    }
}
