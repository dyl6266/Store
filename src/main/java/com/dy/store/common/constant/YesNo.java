package com.dy.store.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum YesNo {

	/** 데이터 삭제 여부, 데이터 상태 등을 나타내는 상태 값 */
	Y(1, true), N(0, false);

	private int firstValue;
	private boolean secondValue;

}
