package com.dy.store.authority.service;

import org.springframework.stereotype.Service;

import com.dy.store.authority.constant.Authority;
import com.dy.store.authority.domain.AuthorityDto;
import com.dy.store.authority.mapper.AuthorityMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthorityServiceImpl implements AuthorityService {

	private AuthorityMapper authorityMapper;

	/**
	 * 사용자 권한 등록
	 * 
	 * @param params - AuthorityDto
	 * @return boolean
	 */
	@Override
	public boolean registerUserAuthority(AuthorityDto params) {

		int queryResult = authorityMapper.insertUserAuthority(params);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	/**
	 * 사용자 권한 조회
	 * 
	 * @param params - AuthorityDto
	 * @return 사용자 권한
	 */
	@Override
	public AuthorityDto getUserAuthorityDetail(AuthorityDto params) {
		return authorityMapper.selectUserAuthorityDetail(params);
	}

	/**
	 * 사용자 권한 조회
	 * 
	 * @param email - 아이디
	 * @param name - 권한명
	 * @return 사용자 권한
	 */
	@Override
	public AuthorityDto getUserAuthorityDetail(String email, Authority name) {

		AuthorityDto params = AuthorityDto.builder().email(email).name(name).build();
		return authorityMapper.selectUserAuthorityDetail(params);
	}

	/**
	 * 사용자 권한 상태 변경
	 * 
	 * @param params - AuthorityDto
	 * @return boolean
	 */
	@Override
	public boolean changeUserAuthorityStatus(AuthorityDto params) {

		int queryResult = authorityMapper.updateUserAuthorityStatus(params);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	/**
	 * 사용자 권한 상태 변경
	 * 
	 * @param email - 아이디
	 * @param params - AuthorityDto
	 * @return boolean
	 */
	@Override
	public boolean changeUserAuthorityStatus(String email, AuthorityDto params) {

		int queryResult = authorityMapper.updateUserAuthorityStatus(params);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

}
