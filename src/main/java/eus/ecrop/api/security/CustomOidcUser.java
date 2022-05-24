package eus.ecrop.api.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import eus.ecrop.api.domain.User;

/*
* @author Mikel Orobengoa
* @version 19/05/2022
*/

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomOidcUser other = (CustomOidcUser) obj;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

    
   
}
