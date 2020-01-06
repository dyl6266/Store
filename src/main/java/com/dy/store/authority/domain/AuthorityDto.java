package com.dy.store.authority.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.dy.store.authority.constant.Authority;
import com.dy.store.common.domain.CommonDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class AuthorityDto extends CommonDto {

	/** PK */
	private Long id;

	/** 아이디 (FK) */
	@NotBlank(message = "아이디를 입력해 주세요.")
	private String email;

	/** 권한명 */
	@NotNull(message = "권한명을 입력해 주세요.")
	private Authority name;

	@Builder
	public AuthorityDto(Long id, String email, Authority name) {
		this.id = id;
		this.email = email;
		this.name = name;
	}

}
