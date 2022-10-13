package com.internationalmessenger.api.security.oauth2;

import com.internationalmessenger.api.entity.Role;
import com.internationalmessenger.api.entity.User;
import com.internationalmessenger.api.entity.enums.EAuthProvider;
import com.internationalmessenger.api.entity.enums.ERole;
import com.internationalmessenger.api.exception.OAuth2AuthenticationProcessingException;
import com.internationalmessenger.api.repository.RoleRepository;
import com.internationalmessenger.api.repository.UserRepository;
import com.internationalmessenger.api.security.oauth2.user.OAuth2UserInfo;
import com.internationalmessenger.api.security.oauth2.user.OAuth2UserInfoFactory;
import com.internationalmessenger.api.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );

        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getProvider().equals(EAuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                EAuthProvider provider = user.getProvider();
                throw new OAuth2AuthenticationProcessingException(
                        "Looks like you are signed up with " + provider + " account. Please use your " + provider + " account to login"
                );
            }
            if (!user.getIsNonLocked()) {
                throw new OAuth2AuthenticationProcessingException("User account is locked");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserDetailsImpl.build(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        Role role = roleRepository.findByName(ERole.USER).orElse(null);
        User user = new User();
        user.setProvider(EAuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImage(oAuth2UserInfo.getImageUrl());
        user.setRole(role);
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        if (existingUser.getImage() == null) {
            existingUser.setImage(oAuth2UserInfo.getImageUrl());
        }
        return userRepository.save(existingUser);
    }
}
