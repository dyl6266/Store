package com.dy.store.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.dy.store.user.domain.UserDto;
import com.dy.store.user.service.UserService;

public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private UserService userService;

	/**
	 * 로그인에 실패하면 실행되는 메소드
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		final String email = request.getParameter("email");

		/* 세션에 아이디, 에러 메시지 저장 */
		request.setAttribute("email", email);
		request.setAttribute("message", exception.getMessage());

		/* 비밀번호가 틀린 경우에만 실패 카운트 증가 (아이디가 존재하지 않거나, 이미 계정이 잠겨 있는 경우에는 Update를 할 수 없음) */
		if (exception instanceof BadCredentialsException) {
			UserDto user = (UserDto) userService.loadUserByUsername(email);
			/* 사용자 로그인 실패 수 증가 */
			userService.modifyUserLoginFailureCount(email, user.getFailedCount() + 1);
		}

		super.setDefaultFailureUrl(request.getContextPath() + "/login?fail");
		super.setUseForward(true);
		super.onAuthenticationFailure(request, response, exception);
	}

}
