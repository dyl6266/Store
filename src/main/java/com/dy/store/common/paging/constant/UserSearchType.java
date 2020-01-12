package com.dy.store.common.paging.constant;

import com.dy.store.common.paging.Types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserSearchType implements Types {

	/** 사용자 검색 유형 */
	EMAIL("아이디"), NICKNAME("닉네임"), BIRTHDAY("생년월일");

	private String value;

}
