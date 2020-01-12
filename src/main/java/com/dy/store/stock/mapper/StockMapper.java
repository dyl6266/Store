package com.dy.store.stock.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dy.store.stock.domain.StockDto;

@Mapper
public interface StockMapper {

	/**
	 * 상품 재고 등록
	 * 
	 * @param params
	 * @return 쿼리 실행 수
	 */
	public int insertStock(StockDto params);

	/**
	 * 상품 재고 정보 조회
	 * 
	 * @param code - 상품 코드
	 * @return 상품 재고 정보
	 */
	public StockDto selectStockDetail(String code);

	/**
	 * 상품 재고 수정
	 * 
	 * @param params
	 * @return 쿼리 실행 수
	 */
	public int updateStock(StockDto params);

}
