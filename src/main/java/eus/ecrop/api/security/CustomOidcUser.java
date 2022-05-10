package eus.ecrop.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import lombok.Getter;
import lombok.Setter;

/*
* @author Mikel Orobengoa
* @version 10/05/2022
*/

@Getter @Setter
public class CustomOidcUser extends DefaultOidcUser {

    private OAuth2AccessToken oAuth2AccessToken;

    public CustomOidcUser(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken,
            OidcUserInfo userInfo) {
        super(authorities, idToken, userInfo);
    }

    public CustomOidcUser(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken,
            OidcUserInfo userInfo, OAuth2AccessToken oAuth2AccessToken) {
        super(authorities, idToken, userInfo);
        this.oAuth2AccessToken = oAuth2AccessToken;

    }

}
