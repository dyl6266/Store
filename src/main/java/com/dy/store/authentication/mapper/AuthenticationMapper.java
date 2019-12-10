package com.dy.store.authentication.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dy.store.authentication.domain.AuthenticationDto;
import com.dy.store.common.constant.YesNo;

@Mapper
public interface AuthenticationMapper {

	/**
	 * 인증 정보 등록
	 * 
	 * @param params - AuthenticationDto
	 * 
	 * @return 쿼리 실행 수
	 */
	public int insertAuthenticationInfo(AuthenticationDto params);

	/**
	 * 인증 정보 조회
	 * 
	 * @param params - AuthenticationDto
	 * 
	 * @return AuthenticationDto
	 */
	public AuthenticationDto selectAuthenticationDetail(AuthenticationDto params);

	/**
	 * 인증 상태 변경
	 * 
	 * @param params - AuthenticationDto
	 * 
	 * @return 쿼리 실행 수
	 */
	public int updateAuthenticationStatus(AuthenticationDto params);

	/**
	 * 인증 상태 조회
	 * 
	 * @param email - 아이디
	 * 
	 * @return 인증 상태
	 */
	public YesNo selectAuthenticationStatus(String email);

}
