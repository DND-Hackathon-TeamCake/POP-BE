package com.cake.pop.global.auth.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.cake.pop.global.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {

		log.error("비인가 사용자 요청 -> 예외 발생 : {}", authException.getMessage());

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 401 Unauthorized
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		ErrorResponse errorResponse = new ErrorResponse(SecurityErrorCode.UNAUTHORIZED_USER.getMessage(),
			SecurityErrorCode.UNAUTHORIZED_USER.getMessage());

		ObjectMapper mapper = new ObjectMapper();
		String jsonResponse = mapper.writeValueAsString(errorResponse);

		response.getWriter().write(jsonResponse);
	}
}
