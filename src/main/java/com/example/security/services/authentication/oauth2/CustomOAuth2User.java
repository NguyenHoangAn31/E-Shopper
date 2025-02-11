package com.example.security.services.authentication.oauth2;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User extends DefaultOAuth2User {

    public CustomOAuth2User(OAuth2User oAuth2User, Collection<? extends GrantedAuthority> authorities) {
        super(authorities, oAuth2User.getAttributes(), "email");

    }

    public String getEmail() {
        return getAttribute("email");
    }

    @Override
    public Map<String, Object> getAttributes() {
        return super.getAttributes();
    }

}