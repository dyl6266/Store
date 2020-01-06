package com.dy.store.authority.service;

import com.dy.store.authority.constant.Authority;
import com.dy.store.authority.domain.AuthorityDto;

public interface AuthorityService {

	/**
	 * 사용자 권한 등록
	 * 
	 * @param params - AuthorityDto
	 * @return boolean
	 */
	public boolean registerUserAuthority(AuthorityDto params);

	/**
	 * 사용자 권한 조회
	 * 
	 * @param params - AuthorityDto
	 * @return 사용자 권한
	 */
	public AuthorityDto getUserAuthorityDetail(AuthorityDto params);

	/**
	 * 사용자 권한 조회
	 * 
	 * @param email - 아이디
	 * @param name - 권한명
	 * @return 사용자 권한
	 */
	public AuthorityDto getUserAuthorityDetail(String email, Authority name);

	/**
	 * 사용자 권한 상태 변경
	 * 
	 * @param params - AuthorityDto
	 * @return boolean
	 */
	public boolean changeUserAuthorityStatus(AuthorityDto params);

	/**
	 * 사용자 권한 상태 변경
	 * 
	 * @param email - 아이디
	 * @param params - AuthorityDto
	 * @return boolean
	 */
	public boolean changeUserAuthorityStatus(String email, AuthorityDto params);

}
