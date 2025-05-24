package com.cake.pop.global.auth.oauth;



import static com.cake.pop.global.auth.oauth.AuthErrorCode.*;
import static com.cake.pop.global.auth.oauth.Provider.*;

import java.util.Objects;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cake.pop.domain.user.service.UserService;
import com.cake.pop.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

	private final UserService userService;

	@Transactional
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		String oauth2AccessToken = userRequest.getAccessToken().getTokenValue();

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		Oauth2Response oauth2Response = null;

		if (Objects.equals(registrationId, KAKAO.getLabel())) {
			oauth2Response = new KakaoResponse(oAuth2User.getAttributes(), oauth2AccessToken);
		} else {
			throw new OAuth2AuthenticationException(
				new OAuth2Error(AuthErrorCode.UNSUPPORTED_SOCIAL_LOGIN.getMessage()),
				AuthErrorCode.UNSUPPORTED_SOCIAL_LOGIN.getMessage()
			);
		}

		User savedUser = userService.saveOrUpdate(oauth2Response);

		AuthInfo authInfo = AuthInfo.of(
			savedUser.getEmail()
		);
		return new CustomOauth2User(authInfo);
	}

}

