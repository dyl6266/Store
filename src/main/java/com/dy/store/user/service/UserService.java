package com.dy.store.user.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.dy.store.user.domain.UserDto;

/* 데이터베이스에서 사용자의 정보를 직접 가지고 올 때 사용하는 시큐리티 인터페이스 상속 */
public interface UserService extends UserDetailsService {

	/**
	 * 사용자 등록 (Insert or Update)
	 * 
	 * @param params - UserDto
	 * 
	 * @return boolean
	 */
	public boolean registerUser(UserDto params);

	/**
	 * 사용자 정보 조회
	 * 
	 * @param params - UserDto
	 * 
	 * @return UserDto
	 */
	public UserDto getUserDetail(UserDto params);

	/**
	 * 사용자 삭제
	 * 
	 * @param email - 아이디
	 * 
	 * @return boolean
	 */
	public boolean deleteUser(String email);

	/**
	 * 사용자 목록 조회
	 * 
	 * @return 사용자 목록
	 */
	public List<UserDto> getUserList();

}
