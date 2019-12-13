package com.dy.store.authority.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Authority {

	/** 권한명 */
	MEMBER("회원"), MANAGER("매니저"), ADMIN("관리자");

	private String value;

}
