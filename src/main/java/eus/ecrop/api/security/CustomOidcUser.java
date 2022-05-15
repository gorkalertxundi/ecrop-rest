package eus.ecrop.api.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import eus.ecrop.api.domain.User;

public class CustomOidcUser extends DefaultOidcUser {

    private User user;

    public CustomOidcUser(User user, OidcIdToken idToken,
            OidcUserInfo userInfo) {
        super(user.getRole().getPrivileges().stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.getCode())).collect(Collectors.toSet()), idToken,
                userInfo, IdTokenClaimNames.SUB);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
   
}
