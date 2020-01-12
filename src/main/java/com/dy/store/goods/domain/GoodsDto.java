package com.dy.store.goods.domain;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.dy.store.common.domain.CommonDto;
import com.dy.store.goods.constant.Status;
import com.dy.store.stock.domain.StockDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class GoodsDto extends CommonDto {

	/** PK */
	private Long id;

	/** 상품 코드 */
	private String code;

	/** 상품명 */
	@NotBlank(message = "상품명을 입력해 주세요.")
	private String name;

	/** 설명 */
	@NotBlank(message = "설명을 입력해 주세요.")
	private String description;

	/** 가격 */
	@NotNull(message = "가격을 입력해 주세요.")
	@Min(value = 1000, message = "가격을 1,000원 이상으로 입력해 주세요.")
	private Integer price;

	/** 할인율 */
	@Min(value = 0, message = "할인율은 0보다 작을 수 없습니다.")
	@Max(value = 99, message = "할인율은 99보다 클 수 없습니다.")
	private Integer rate;

	/** 판매 상태 */
	@NotNull(message = "판매 상태를 선택해 주세요.")
	private Status status;

	/** 재고 정보 */
	@Valid
	private StockDto stock;

	@Builder
	public GoodsDto(Long id, String code, String name, String description, Integer price, Integer rate, Status status,
			StockDto stock) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.price = price;
		this.rate = rate;
		this.status = status;
		this.stock = stock;
	}

}
