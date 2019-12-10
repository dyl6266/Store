package com.dy.store.user.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dy.store.authentication.mapper.AuthenticationMapper;
import com.dy.store.common.constant.YesNo;
import com.dy.store.user.domain.UserDto;
import com.dy.store.user.mapper.UserMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private UserMapper userMapper;

	private AuthenticationMapper authMapper;

	private PasswordEncoder passwordEncoder;

	/**
	 * 사용자 등록 (Insert or Update)
	 * 
	 * @param params - UserDto
	 * 
	 * @return boolean
	 */
	@Override
	public boolean registerUser(UserDto params) {

		/* 패스워드 인코딩 (Update의 경우에는 패스워드가 파라미터로 넘어오는 경우에만 실행되어야 함) */
		Optional.ofNullable(params.getPassword()).ifPresent((value -> {
			params.setPassword(passwordEncoder.encode(params.getPassword()));
		}));

		UserDto user = null; /* 아이디, 닉네임 중복 확인에 사용할 파라미터 */
		int userCount = 0; /* 파라미터로 넘어온 아이디 또는 닉네임을 가지고 있는 사용자 수 (Unique Key 중복 방지) */
		int queryResult = 0; /* Insert, Update 쿼리 실행 결과 */

		/* Insert (파라미터에 PK가 존재하지 않는 경우) */
		if (params.getId() == null) {
			/* 인증번호 확인이 정상적으로 처리되었는지 확인 */
			YesNo status = authMapper.selectAuthenticationStatus(params.getEmail());
			if (status == YesNo.N) {
				return false;
			}

			/* 아이디, 닉네임 중복 확인 */
			user = UserDto.builder().email(params.getEmail()).nickname(params.getNickname()).build();
			userCount = userMapper.selectUserTotalCount(user);
			if (userCount > 0) {
				return false;
			}

			queryResult = userMapper.insertUser(params);
			if (queryResult != 1) {
				return false;
			}

		/* Update (파라미터에 PK가 존재하는 경우) */
		} else {
			user = UserDto.builder().nickname(params.getNickname()).build();
			userCount = userMapper.selectUserTotalCount(user);
			if (userCount > 0) {
				return false;
			}

			queryResult = userMapper.updateUser(params);
			if (queryResult != 1) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 사용자 정보 조회
	 * 
	 * @param params - UserDto
	 * 
	 * @return UserDto
	 */
	@Override
	public UserDto getUserDetail(UserDto params) {
		return userMapper.selectUserDetail(params);
	}

	/**
	 * 데이터베이스에서 사용자의 정보를 직접 가지고 올 때 사용하는 시큐리티 메소드
	 * 
	 * @param email - 아이디
	 * 
	 * @return UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userMapper.selectUserDetailByEmail(email);
	}

	/**
	 * 사용자 삭제
	 * 
	 * @param email - 아이디
	 * 
	 * @return boolean
	 */
	@Override
	public boolean deleteUser(String email) {

		int queryResult = userMapper.deleteUser(email);
		if (queryResult != 1) {
			return false;
		}

		return false;
	}

	/**
	 * 사용자 목록 조회
	 * 
	 * @return UserDto
	 */
	@Override
	public List<UserDto> getUserList() {

		List<UserDto> userList = Collections.emptyList();

		int userTotalCount = userMapper.selectUserTotalCount(null);
		if (userTotalCount > 0) {
			userList = userMapper.selectUserList();
		}

		return userList;
	}

}
