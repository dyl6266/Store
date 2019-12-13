package com.dy.store.user.domain;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dy.store.common.domain.CommonDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class UserDto extends CommonDto implements UserDetails {

	private static final long serialVersionUID = -5578099000133227997L;

	/** PK */
	private Long id;

	/** 아이디 (UK) */
	@Pattern(message = "아이디를 올바른 형식으로 입력해 주세요.", regexp = "^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{1,5}$")
	private String email;

	/** 별명 */
	@Pattern(message = "닉네임을 올바른 형식으로 입력해 주세요.", regexp = "^[가-힣]{2,10}$")
	private String nickname;

	/** 비밀번호 */
	@Pattern(message = "비밀번호를 올바른 형식으로 입력해 주세요.", regexp = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~`!@#$%\\^&*()-]).{8,20}$")
	private String password;

	/** 생일 */
	@Pattern(message = "생년월일을 올바른 형식으로 입력해 주세요.", regexp = "^(19[0-9][0-9]|20\\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$")
	private String birthday;

	/** 로그인 실패 횟수 */
	private int failedCount;

	/** 마지막 로그인 날짜 */
	private LocalDateTime lastLoginDate;

	/** 권한 리스트 (Not a column) */
	private Collection<? extends GrantedAuthority> authorities;

	/** 아이디 (Not a column) */
	private String username;

	/** 계정 만료 여부 (Not a column) */
	private boolean accountNonExpired = true;

	/** 계정 잠김 여부 (Not a column) */
	private boolean accountNonLocked = true;

	/** 비밀번호 만료 여부 (Not a column) */
	private boolean credentialsNonExpired = true;

	/** 계정 활성화 여부 (Not a column) */
	private boolean enabled = true;

	@Builder
	public UserDto(Long id, String email, String nickname, String password, String birthday, int failedCount) {
		this.id = id;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.birthday = birthday;
		this.failedCount = failedCount;
	}

}
