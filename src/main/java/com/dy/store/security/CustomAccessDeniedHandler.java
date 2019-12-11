package com.dy.store.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

public class CustomAccessDeniedHandler extends AccessDeniedHandlerImpl {

	/**
	 * 접근이 거부되었을 때 실행되는 메소드
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		super.setErrorPage(request.getContextPath() + "/access-denied");
		super.handle(request, response, accessDeniedException);
	}

}
