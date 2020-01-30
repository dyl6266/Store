package com.dy.store.attach.service;

import java.util.List;

import com.dy.store.attach.domain.AttachDto;

public interface AttachService {

	/**
	 * 단일 파일 삭제
	 * 
	 * @param id - PK
	 * @param code - 상품 코드
	 * @return boolean
	 */
	public boolean deleteAttach(Long id, String code);

	/**
	 * 전체 파일 삭제
	 * 
	 * @param code - 상품 코드
	 * @return boolean
	 */
	public boolean deleteAttach(String code);

	/**
	 * 파일 목록 조회
	 * 
	 * @param code - 상품 코드
	 * @return 파일 목록
	 */
	public List<AttachDto> getAttachList(String code);

}
