package com.cake.pop.global.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.cake.pop.global.auth.handler.CustomAccessDeniedHandler;
import com.cake.pop.global.auth.handler.CustomAuthenticationEntryPoint;
import com.cake.pop.global.auth.handler.CustomOauth2FailureHandler;
import com.cake.pop.global.auth.handler.CustomOauth2SuccessHandler;
import com.cake.pop.global.auth.jwt.TokenAuthenticationFilter;
import com.cake.pop.global.auth.jwt.TokenExceptionFilter;
import com.cake.pop.global.auth.oauth.CustomOauth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomOauth2UserService customOauth2UserService;
	private final CustomOauth2SuccessHandler customOauth2SuccessHandler;
	private final CustomOauth2FailureHandler customOauth2FailureHandler;
	private final TokenAuthenticationFilter tokenAuthenticationFilter;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf((auth) -> auth.disable())
			.formLogin((auth) -> auth.disable())
			.httpBasic((auth) -> auth.disable())
			.sessionManagement(
				(session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);

		http
			.authorizeHttpRequests(
				(auth) -> auth
					.requestMatchers("/").permitAll()
					.requestMatchers("/api/auth/reissue/token",
						"/login/oauth2/**",         // ← 이 줄 추가
						"/oauth2/authorization/**").permitAll()
			)
			.oauth2Login((oauth2) -> oauth2
				.userInfoEndpoint(
					(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOauth2UserService))
				)
				.successHandler(customOauth2SuccessHandler)
				.failureHandler(customOauth2FailureHandler)
			)

			.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new TokenExceptionFilter(), tokenAuthenticationFilter.getClass())

			.exceptionHandling((exception) -> exception
				.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
				.accessDeniedHandler(customAccessDeniedHandler)
			);

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
			.requestMatchers(
				"/error", "/favicon.ico", "/api/auth/temp-signup", "/api/auth/temp-signin",
				"/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/ws/**"
			);
	}

	// Spring Security cors Bean 등록
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(
			Arrays.asList(
				"http://localhost:3000", "https://cake-pop.vercel.app",
				"https://www.cake-pop.shop", "https://cake-pop.shop", "http://localhost:8080",
				"https://dev.cake-pop.shop"
			));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setExposedHeaders(Arrays.asList("Set-Cookie", "Authorization"));
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3000L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
