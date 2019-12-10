package com.dy.store.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dy.store.user.domain.UserDto;

@Mapper
public interface UserMapper {

	/**
	 * 사용자 등록
	 * 
	 * @param params - UserDto
	 * 
	 * @return 쿼리 실행 수
	 */
	public int insertUser(UserDto params);

	/**
	 * 사용자 정보 조회
	 * 
	 * @param params - UserDto
	 * 
	 * @return UserDto
	 */
	public UserDto selectUserDetail(UserDto params);

	/**
	 * 사용자 정보 조회
	 * 
	 * @param email - 아이디
	 * 
	 * @return UserDto
	 */
	public UserDto selectUserDetailByEmail(String email);

	/**
	 * 사용자 정보 수정
	 * 
	 * @param params - UserDto
	 * 
	 * @return 쿼리 실행 수
	 */
	public int updateUser(UserDto params);

	/**
	 * 사용자 삭제
	 * 
	 * @param email - 아이디
	 *  
	 * @return 쿼리 실행 수
	 */
	public int deleteUser(String email);

	/**
	 * 사용자 목록 조회
	 * 
	 * @return 사용자 목록
	 */
	public List<UserDto> selectUserList();

	/**
	 * 전체 사용자 수 조회
	 * 
	 * @param params - UserDto
	 * 
	 * @return 전체 사용자 수
	 */
	public int selectUserTotalCount(UserDto params);

}
