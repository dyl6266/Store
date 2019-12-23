package com.dy.store.user.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dy.store.authentication.mapper.AuthenticationMapper;
import com.dy.store.authority.constant.Authority;
import com.dy.store.authority.domain.AuthorityDto;
import com.dy.store.authority.mapper.AuthorityMapper;
import com.dy.store.common.constant.YesNo;
import com.dy.store.common.paging.PaginationInfo;
import com.dy.store.user.domain.UserDto;
import com.dy.store.user.mapper.UserMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private UserMapper userMapper;

	private AuthenticationMapper authenticationMapper;

	private AuthorityMapper authorityMapper;

	private PasswordEncoder passwordEncoder;

	/**
	 * 사용자 등록 (Insert or Update)
	 * 
	 * @param params - UserDto
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
			YesNo status = authenticationMapper.selectAuthenticationStatus(params.getEmail());
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

			/* 권한 등록 */
			AuthorityDto authority = AuthorityDto.builder().email(params.getEmail()).name(Authority.MEMBER).build();
			queryResult = authorityMapper.insertUserAuthority(authority);
			if (queryResult != 1) {
				return false;
			}

			/* Update (파라미터에 PK가 존재하는 경우) */
		} else {
			/* 파라미터에 닉네임이 존재하는 경우 */
			if (Strings.isBlank(params.getNickname()) == false) {
				user = UserDto.builder().nickname(params.getNickname()).build();
				userCount = userMapper.selectUserTotalCount(user);
				if (userCount > 0) {
					return false;
				}
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
	 * @return 사용자 정보
	 */
	@Override
	public UserDto getUserDetail(UserDto params) {
		return userMapper.selectUserDetail(params);
	}

	/**
	 * 사용자 정보 조회
	 * 
	 * @param param - 아이디 또는 닉네임
	 * @return 사용자 정보
	 */
	@Override
	public UserDto getUserDetail(String param) {
		return userMapper.selectUserDetailByEmailOrNickname(param);
	}

	/**
	 * 데이터베이스에서 사용자의 정보를 직접 가지고 올 때 사용하는 시큐리티 메소드
	 * 
	 * @param email - 아이디
	 * @return 사용자 정보
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserDto user = userMapper.selectUserDetailByEmailOrNickname(email);
		if (user != null) {
			/* 사용자의 권한 목록을 담을 리스트 */
			List<GrantedAuthority> authorities = Collections.emptyList();

			int totalCount = authorityMapper.selectUserAuthorityTotalCount(email);
			if (totalCount > 0) {
				authorities = authorityMapper.selectUserAuthorityList(email);
				user.setAuthorities(authorities);
			}
		}

		return user;
	}

	/**
	 * 사용자 삭제
	 * 
	 * @param email - 아이디
	 * @return boolean
	 */
	@Override
	public boolean deleteUser(String email) {

		int queryResult = userMapper.deleteUser(email);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	/**
	 * 사용자 목록 조회
	 * 
	 * @param params - UserDto
	 * @return 사용자 목록
	 */
	@Override
	public List<UserDto> getUserList(UserDto params) {

		/* 사용자 목록을 담을 비어있는 리스트 */
		List<UserDto> userList = Collections.emptyList();
		/* 전체 사용자 수 */
		int userTotalCount = userMapper.selectUserTotalCount(params);

		/* 페이징 계산에 필요한 전체 회원 수 저장 */
		params.setTotalRecordCount(userTotalCount);
		/* Pagination 클래스에서 페이징 정보를 계산하고, 계산이 완료된 paginationInfo를 params에 저장 */
		params.setPaginationInfo(new PaginationInfo(params));

		if (userTotalCount > 0) {
			/* 사용자 목록 조회 */
			userList = userMapper.selectUserList(params);

			/* 사용자 권한 목록 저장 */
			for (UserDto user : userList) {
				List<GrantedAuthority> authorities = authorityMapper.selectUserAuthorityList(user.getEmail());
				user.setAuthorities(authorities);

				/* 권한명 저장 */
				List<String> authorityNames = new ArrayList<>();
				for (GrantedAuthority grantedAuthority : authorities) {
					/* Authority의 모든 권한 상수 중 GrantedAuthority를 포함하는 문자열이 있는 경우 권한명 추가 (if 조건이 들어가지 않으면 모든 권한의 권한명이 추가됨) */
					for (Authority authority : Authority.values()) {
						if (String.valueOf(grantedAuthority).contains(String.valueOf(authority)) == true) {
							authorityNames.add(authority.getValue());
						}
					}
				}
				// end of authorities forEach

				user.setAuthorityNames(authorityNames);
			}
			// end of userList forEach
		}

		return userList;
	}

	/**
	 * 로그인 실패 횟수 변경
	 * 
	 * @param email - 아이디
	 * @param failedCount - 로그인 실패 수
	 * @return boolean
	 */
	@Override
	public boolean modifyUserLoginFailureCount(String email, int failedCount) {

		UserDto user = userMapper.selectUserDetailByEmailOrNickname(email);
		if (user == null) {
			return false;
		}

		UserDto params = UserDto.builder().email(email).failedCount(failedCount).build();

		int queryResult = userMapper.updateUserLoginFailureCount(params);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

}
