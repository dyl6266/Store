package com.dy.store.goods.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dy.store.goods.domain.GoodsDto;

@Mapper
public interface GoodsMapper {

	/**
	 * 상품 등록
	 * 
	 * @param params - GoodsDto
	 * @return 쿼리 실행 수
	 */
	public int insertGoods(GoodsDto params);

	/**
	 * 상품 정보 조회
	 * 
	 * @param code - 상품 코드
	 * @return 상품 정보
	 */
	public GoodsDto selectGoodsDetail(String code);

	/**
	 * 상품 정보 수정
	 * 
	 * @param params - GoodsDto
	 * @return 쿼리 실행 수
	 */
	public int updateGoods(GoodsDto params);

	/**
	 * 상품 삭제
	 * 
	 * @param codeList - 상품 코드 목록
	 * @return 쿼리 실행 수
	 */
	public int deleteGoods(List<String> codeList);

	/**
	 * 상품 목록 조회
	 * 
	 * @params - GoodsDto
	 * @return 상품 목록
	 */
	public List<GoodsDto> selectGoodsList(GoodsDto params);

	/**
	 * 전체 상품 수 조회
	 * 
	 * @params - GoodsDto
	 * @return 전체 상품 수
	 */
	public int selectGoodsTotalCount(GoodsDto params);

}
