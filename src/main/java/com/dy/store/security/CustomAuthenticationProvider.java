package com.dy.store.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dy.store.user.domain.UserDto;
import com.dy.store.user.service.UserService;

import lombok.AllArgsConstructor;

/* 클라이언트가 입력한 정보와 데이터베이스에서 가지고 온 사용자 정보를 비교할 때 사용하는 인터페이스 구현 */
@AllArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private UserService userService;

	private PasswordEncoder passwordEncoder;

	/**
	 * 로그인 인증 처리
	 * 
	 * @param authentication - 클라이언트가 입력한 정보를 담는 오브젝트
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		final String email = authentication.getName();
		final String password = (String) authentication.getCredentials();

		UserDto user = (UserDto) userService.loadUserByUsername(email);
		if (user == null) {
			/* 존재하지 않는 아이디일 때 던지는 예외 */
			throw new InternalAuthenticationServiceException("아이디 또는 비밀번호를 확인해 주세요.");

		} else if (passwordEncoder.matches(password, user.getPassword()) == false) {
			/* 일치하지 않은 비밀번호일 때 던지는 예외 */
			throw new BadCredentialsException("아이디 또는 비밀번호를 확인해 주세요.");

		} else if (user.getFailedCount() >= 5) {
			/* 인증이 거부되었을 때 던지는 예외 (계정 잠김) */
			throw new LockedException("계정이 잠겨 있습니다. 관리자에게 문의해 주세요.");
		}

		return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	}

	/**
	 * AuthenticationProvider 인터페이스가 지정된 authentication 객체를 지원하는 경우 true를 반환
	 * 
	 * @param authentication
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
