package com.dy.store.authority.domain;

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
	private String email;

	/** 권한명 */
	private Authority name;

	@Builder
	public AuthorityDto(Long id, String email, Authority name) {
		this.id = id;
		this.email = email;
		this.name = name;
	}

}
