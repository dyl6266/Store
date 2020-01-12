package com.dy.store.stock.domain;

import javax.validation.constraints.NotBlank;

import com.dy.store.common.domain.CommonDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class StockDto extends CommonDto {

	/** PK */
	private Long id;

	/** 상품 코드 (FK) */
	private String code;

	/** 색상 */
	@NotBlank(message = "상품 색상을 입력해 주세요.")
	private String color;

	/** 옵션 (사이즈, 수량 정보를 JSON 형태로 가지는 인스턴스 변수) */
	@NotBlank(message = "상품 옵션(사이즈, 수량)을 입력해 주세요.")
	private String options;

	@Builder
	public StockDto(Long id, String code, String color, String options) {
		this.id = id;
		this.code = code;
		this.color = color;
		this.options = options;
	}

}
