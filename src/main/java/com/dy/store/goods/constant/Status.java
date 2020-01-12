package com.dy.store.goods.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {

	/** 상품 판매 상태 */
	NORMAL("일반"), DISCOUNT("할인"), SOLDOUT("품절");

	private String value;

}
