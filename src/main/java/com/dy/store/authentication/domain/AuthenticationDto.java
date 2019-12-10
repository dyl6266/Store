package com.dy.store.authentication.domain;

import javax.validation.constraints.Pattern;

import com.dy.store.common.domain.CommonDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class AuthenticationDto extends CommonDto {

	/** PK */
	private Long id;

	/** 이메일 */
	@Pattern(message = "아이디를 올바른 형식으로 입력해 주세요.", regexp = "^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{1,5}$")
	private String email;

	/** 인증번호 */
	private String number;

	@Builder
	public AuthenticationDto(String email, String number) {
		this.email = email;
		this.number = number;
	}

}
