package com.dy.store.authentication.service;

import com.dy.store.authentication.domain.AuthenticationDto;

public interface AuthenticationService {

	/**
	 * 인증 메일 발송
	 * 
	 * @param email - 아이디
	 * 
	 * @return boolean
	 */
	public boolean sendAuthenticationMail(String email);

	/**
	 * 인증 정보 조회
	 * 
	 * @param email - 아이디
	 * @param number - 인증번호
	 * 
	 * @return AuthenticationDto
	 */
	public AuthenticationDto getAuthenticationDetail(String email, String number);

	/**
	 * 인증 상태 변경
	 * 
	 * @param email - 아이디
	 * @param number - 인증번호
	 * 
	 * @return boolean
	 */
	public boolean modifyAuthenticationStatus(String email, String number);

}
