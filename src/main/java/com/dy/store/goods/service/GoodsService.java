package com.dy.store.goods.service;

import java.util.List;

import com.dy.store.goods.domain.GoodsDto;

public interface GoodsService {

	/**
	 * 상품 등록 (Insert or Update)
	 * 
	 * @param params - GoodsDto
	 * @return boolean
	 */
	public boolean registerGoods(GoodsDto params);

	/**
	 * 상품 정보 조회
	 * 
	 * @param code - 상품 코드
	 * @return 상품 정보
	 */
	public GoodsDto getGoodsDetail(String code);

	/**
	 * 상품 삭제
	 * 
	 * @param codeList - 상품 코드 목록
	 * @return boolean
	 */
	public boolean deleteGoods(List<String> codeList);

	/**
	 * 상품 목록 조회
	 * 
	 * @params - GoodsDto
	 * @return 상품 목록
	 */
	public List<GoodsDto> getGoodsList(GoodsDto params);

}
