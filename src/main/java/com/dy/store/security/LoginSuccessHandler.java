package com.dy.store.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import com.dy.store.user.service.UserService;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserService userService;

	/* Spring Security에서 제공하는 클라이언트의 요청을 저장하고 불러올 수 있는 인터페이스 */
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	/**
	 * 로그인에 성공하면 실행되는 메소드
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		/* 사용자 로그인 실패 수 0으로 초기화 */
		userService.modifyUserLoginFailureCount(request.getParameter("email"), 0);

		/* 로그인 실패 에러 세션 제거 */
		clearAuthenticationAttributes(request);

		/* 로그인 화면 이전의 URI를 가지고 오는 데 사용하는 오브젝트 (WebSecurityConfiguration에 지정된 GET 방식으로 호출하는 URI만 해당) */
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		/* 로그인 화면 이전의 URI를 가지고 오는 데 사용하는 오브젝트 (WebSecurityConfiguration에 지정된 GET이 아닌 방식으로 호출하는 URI만 해당) */
		String previousUri = (String) request.getSession().getAttribute("referer");

		if (savedRequest != null) {
			/* 권한이 필요한 페이지로 이동하는 경우 */
			String targetUri = savedRequest.getRedirectUrl();
			redirectStrategy.sendRedirect(request, response, targetUri);

		} else if (StringUtils.isEmpty(previousUri) == false && previousUri.contains("login") == false) {
			/* 로그인이 필요한 상태에서 이전 페이지 정보를 가지는 경우 */
			redirectStrategy.sendRedirect(request, response, previousUri);

		} else {
			/* 직접 로그인 페이지로 이동하는 경우 */
			redirectStrategy.sendRedirect(request, response, request.getContextPath() + "/index");
		}
	}

	/**
	 * 로그인 실패 에러 세션 제거
	 * 
	 * @param request
	 */
	protected final void clearAuthenticationAttributes(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}

		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

}
