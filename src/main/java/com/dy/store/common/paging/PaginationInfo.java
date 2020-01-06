package com.dy.store.common.paging;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationInfo {

	/** 페이징 계산에 필요한 파라미터들이 담긴 클래스 (transient 한정자 => Gson 순환 참조 오류 처리) */
	private transient Criteria criteria;

	/** 전체 페이지 개수 */
	private int totalPageCount;

	/** 페이지 리스트의 첫 페이지 번호 */
	private int firstPage;

	/** 페이지 리스트의 마지막 페이지 번호 */
	private int lastPage;

	/** SQL의 조건절에 사용되는 첫 RNUM */
	private int firstRecordIndex;

	/** SQL의 조건절에 사용되는 마지막 RNUM */
	private int lastRecordIndex;

	/** 이전 페이지 존재 여부 */
	private boolean hasPreviousPage;

	/** 다음 페이지 존재 여부 */
	private boolean hasNextPage;

	/**
	 * 유효하지 않은 값이 들어왔을 때, 기본값 설정
	 * 
	 * @param criteria
	 */
	public PaginationInfo(Criteria criteria) {

		if (criteria.getCurrentPageNo() < 1) {
			criteria.setCurrentPageNo(1);
		}
		if (criteria.getRecordCountPerPage() < 1 || criteria.getRecordCountPerPage() > 50) {
			criteria.setRecordCountPerPage(8);
		}
		if (criteria.getPageSize() < 5 || criteria.getPageSize() > 20) {
			criteria.setPageSize(10);
		}
		this.criteria = criteria;

		if (criteria.getTotalRecordCount() > 0) {
			calculation();
		}
	}

	/**
	 * 페이징 정보 계산
	 */
	private void calculation() {

		/* 전체 페이지 수 (현재 페이지 번호가 전체 페이지 수보다 크면 현재 페이지 번호에 전체 페이지 수를 저장) */
		totalPageCount = ((criteria.getTotalRecordCount() - 1) / criteria.getRecordCountPerPage()) + 1;
		if (criteria.getCurrentPageNo() > totalPageCount) {
			criteria.setCurrentPageNo(totalPageCount);
		}

		/* 페이지 리스트의 첫 페이지 번호 */
		firstPage = ((criteria.getCurrentPageNo() - 1) / criteria.getPageSize()) * criteria.getPageSize() + 1;

		/* 페이지 리스트의 마지막 페이지 번호 (마지막 페이지가 전체 페이지 수보다 크면 마지막 페이지에 전체 페이지 수를 저장) */
		lastPage = firstPage + criteria.getPageSize() - 1;
		if (lastPage > totalPageCount) {
			lastPage = totalPageCount;
		}

		/* SQL의 조건절에 사용되는 첫 RNUM */
		firstRecordIndex = (criteria.getCurrentPageNo() - 1) * criteria.getRecordCountPerPage();

		/* SQL의 조건절에 사용되는 마지막 RNUM */
		lastRecordIndex = criteria.getCurrentPageNo() * criteria.getRecordCountPerPage();

		/* 이전 페이지 존재 여부 */
		hasPreviousPage = firstPage == 1 ? false : true;

		/* 다음 페이지 존재 여부 */
		hasNextPage = (lastPage * criteria.getRecordCountPerPage()) >= criteria.getTotalRecordCount() ? false : true;
	}

	/**
	 * 페이징과 관련된 파라미터들을 쿼리 스트링 형태로 반환
	 * 
	 * @param pageNo - 페이지 번호
	 * @return 쿼리 스트링 (Parameters)
	 */
	public String makeQueryString(int pageNo) {

		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.queryParam("currentPageNo", pageNo)
				.queryParam("recordCountPerPage", criteria.getRecordCountPerPage())
				.queryParam("pageSize", criteria.getPageSize())
				.build()
				.encode();

		return uriComponents.toUriString();
	}

	/**
	 * 검색 + 페이징과 관련된 파라미터들을 쿼리 스트링 형태로 반환
	 * 
	 * @param pageNo - 페이지 번호
	 * @return 쿼리 스트링 (Parameters)
	 */
	public String makeSearchQueryString(int pageNo) {

		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.queryParam("currentPageNo", pageNo)
				.queryParam("recordCountPerPage", criteria.getRecordCountPerPage())
				.queryParam("pageSize", criteria.getPageSize())
				.queryParam("searchType", criteria.getSearchType())
				.queryParam("searchKeyword", criteria.getSearchKeyword())
				.build()
				.encode();

		return uriComponents.toUriString();
	}

}
