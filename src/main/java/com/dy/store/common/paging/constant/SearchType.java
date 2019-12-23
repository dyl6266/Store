package com.dy.store.common.paging.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 검색 유형 공통 클래스
 */
public class SearchType {

	@AllArgsConstructor
	@Getter
	public enum UserSearchType {

		/** 사용자 검색 조건 */
		EMAIL("아이디"), NICKNAME("닉네임"), BIRTHDAY("생년월일");

		private String value;

	}

}
