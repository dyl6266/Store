package com.dy.store.common.paging;

import com.dy.store.common.paging.constant.SearchType.UserSearchType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Criteria {

	/** 현재 페이지 번호 */
	private int currentPageNo;

	/** 페이지당 출력할 데이터 개수 */
	private int recordCountPerPage;

	/** 하단에 출력할 페이지 사이즈 */
	private int pageSize;

	/** 전체 데이터 개수 */
	private int totalRecordCount;

	/** 검색 타입 */
	private UserSearchType searchType;

	/** 검색 키워드 */
	private String searchKeyword;

}
