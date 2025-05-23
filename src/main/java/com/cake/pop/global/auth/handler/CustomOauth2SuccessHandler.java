package com.cake.pop.global.auth.handler;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.cake.pop.domain.user.exception.UserErrorCode;
import com.cake.pop.domain.user.repository.UserRepository;
import com.cake.pop.entity.User;
import com.cake.pop.global.auth.jwt.CookieUtil;
import com.cake.pop.global.auth.jwt.TokenProvider;
import com.cake.pop.global.auth.oauth.CustomOauth2User;
import com.cake.pop.global.exception.RestApiException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomOauth2SuccessHandler implements AuthenticationSuccessHandler {

	private final UserRepository userRepository;
	private final TokenProvider tokenProvider;
	private final CookieUtil cookieUtil;
	@Value("${direct.home}")
	private String REDIRECTION_HOME;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		CustomOauth2User customOauth2User = (CustomOauth2User)authentication.getPrincipal();

		String email = customOauth2User.getEmail();
		User findUser = userRepository.findByEmail(email)
			.orElseThrow(() -> new RestApiException(UserErrorCode.USER_NOT_FOUND));

		String token = tokenProvider.generateAccessToken(findUser, customOauth2User, new Date());
		// tokenProvider.generateRefreshToken(findUser, customOauth2User, new Date());

		response.addCookie(cookieUtil.createCookie(token));

		response.sendRedirect(REDIRECTION_HOME);
	}

	private boolean isRoleGuest(String role) {
		return "ROLE_GUEST".equals(role);
	}
}
