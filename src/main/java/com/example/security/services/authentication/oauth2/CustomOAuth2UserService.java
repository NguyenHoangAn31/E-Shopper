package com.example.security.services.authentication.oauth2;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.security.entities.Role;
import com.example.security.entities.User;
import com.example.security.repositories.RoleRepository;
import com.example.security.repositories.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");

        // nếu người dùng đã đăng ký bằng email thì khi login bằng google thì trả lại dữ liệu mà người dùng đăng ký
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;

        if (userOptional.isEmpty()) {
            user = new User();
            user.setEmail(email);
            user.setImageUrl(oAuth2User.getAttribute("picture"));
            user.setEnabled(true);
            user.setProvider("GOOGLE");
            user.setName(oAuth2User.getAttribute("name"));
            // Add the default USER role
            Role role = roleRepository.findByShortName("USER");
            user.setRoles(Collections.singletonList(role));

            userRepository.save(user);
        } else {
            if (!userOptional.get().isEnabled()) {
                System.out.println("User has been blocked: " + email);
                throw new UsernameNotFoundException("User has been blocked: " + email);
            }
            
            user = userOptional.get();
        }
        return new CustomOAuth2User(oAuth2User, user.getGrantedAuthorities());
    }
}