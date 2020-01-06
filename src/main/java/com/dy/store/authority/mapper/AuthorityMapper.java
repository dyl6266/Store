package com.dy.store.authority.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.GrantedAuthority;

import com.dy.store.authority.domain.AuthorityDto;

@Mapper
public interface AuthorityMapper {

	/**
	 * 사용자 권한 등록
	 * 
	 * @param params - AuthorityDto
	 * @return 쿼리 실행 수
	 */
	public int insertUserAuthority(AuthorityDto params);

	/**
	 * 사용자 권한 조회
	 * 
	 * @param params - AuthorityDto
	 * @return 사용자 권한
	 */
	public AuthorityDto selectUserAuthorityDetail(AuthorityDto params);

	/**
	 * 사용자 권한 상태 변경
	 * 
	 * @param params - AuthorityDto
	 * @return 쿼리 실행 수
	 */
	public int updateUserAuthorityStatus(AuthorityDto params);

	/**
	 * 사용자 권한 목록 조회
	 * 
	 * @param email - 아이디
	 * @return 사용자 권한 목록
	 */
	public List<GrantedAuthority> selectUserAuthorityList(String email);

	/**
	 * 사용자 전체 권한 수 조회
	 * 
	 * @param params - AuthorityDto
	 * @return 사용자 전체 권한 수
	 */
	public int selectUserAuthorityTotalCount(String email);

}
