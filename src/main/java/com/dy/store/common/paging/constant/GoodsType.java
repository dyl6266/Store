package com.dy.store.common.paging.constant;

import com.dy.store.common.paging.Types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GoodsType implements Types {

	/** 상품 검색 유형 (순서) */
	NEW("최신 상품"), BEST("인기 상품"), HIGH("높은 가격"), LOW("낮은 가격");

	private String value;

}
