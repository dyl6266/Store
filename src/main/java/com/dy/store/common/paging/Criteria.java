package com.dy.store.common.paging;

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

	/** 검색 유형 */
//	private List<Types> searchType;
	private Types searchType;

	/** 검색 키워드 */
	private String searchKeyword;

	@Override
	public String toString() {
		return "Criteria [currentPageNo=" + currentPageNo + ", recordCountPerPage=" + recordCountPerPage + ", pageSize="
				+ pageSize + ", totalRecordCount=" + totalRecordCount + ", searchType=" + searchType.getValue()
				+ ", searchKeyword=" + searchKeyword + "]";
	}

}
