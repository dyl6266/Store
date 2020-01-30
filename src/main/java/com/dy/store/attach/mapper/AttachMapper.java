package com.dy.store.attach.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dy.store.attach.domain.AttachDto;

@Mapper
public interface AttachMapper {

	/**
	 * 첨부 파일 등록
	 * 
	 * @param params - AttachDto
	 * @return 쿼리 실행 수
	 */
	public int insertAttach(AttachDto params);

	/**
	 * 첨부 파일 정보 조회
	 * 
	 * @param params - AttachDto
	 * @return 첨부 파일 정보
	 */
	public AttachDto selectAttachDetail(Long id);

	/**
	 * 첨부 파일 삭제
	 * 
	 * @param params - AttachDto
	 * @return 쿼리 실행 수
	 */
	public int deleteAttach(AttachDto params);

	/**
	 * 첨부 파일 목록 조회
	 * 
	 * @param code - 상품 코드
	 * @return 첨부 파일 목록
	 */
	public List<AttachDto> selectAttachList(String code);

	/**
	 * 전체 첨부 파일 수 조회
	 * 
	 * @param code - 상품 코드
	 * @return 전체 첨부 파일 수
	 */
	public int selectAttachTotalCount(String code);

}
