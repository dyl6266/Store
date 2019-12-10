package com.dy.store.common.domain;

import java.time.LocalDateTime;

import com.dy.store.common.constant.YesNo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonDto {

	/** 삭제 여부 */
	private YesNo deleteYn;

	/** 등록일 */
	private LocalDateTime createdDate;

	/** 수정일 */
	private LocalDateTime modifiedDate;

}
