package com.cake.pop.domain.user.service;

import com.cake.pop.domain.user.repository.UserRepository;
import com.cake.pop.entity.User;
import com.cake.pop.global.auth.oauth.Oauth2Response;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User saveOrUpdate(Oauth2Response oauth2Response) {
        User user = userRepository.findFirstByEmail(oauth2Response.getEmail())
            .map(u -> {
                u.updateEmail(oauth2Response.getEmail());
                // deleteRefreshTokenIfExists(m);
                // deleteOauthAccessTokenIfExists(m);
                // saveOauth2AccessToken(oauth2Response, m);
                return u;
            })
            .orElseGet(() -> createMemberFromOauth2Response(oauth2Response));

        return userRepository.save(user);
    }

    private User createMemberFromOauth2Response(Oauth2Response oauth2Response) {
        User user = User.of(oauth2Response.getEmail());
        // saveOauth2AccessToken(oauth2Response, member);
        return user;
    }
}
