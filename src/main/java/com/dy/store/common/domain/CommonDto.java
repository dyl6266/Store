package com.dy.store.common.domain;

import java.time.LocalDateTime;

import com.dy.store.common.constant.YesNo;
import com.dy.store.common.paging.Criteria;
import com.dy.store.common.paging.PaginationInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommonDto extends Criteria {

	/** 삭제 여부 */
	private YesNo deleteYn;

	/** 등록일 */
	private LocalDateTime createdDate;

	/** 수정일 */
	private LocalDateTime modifiedDate;

	/** 페이징 정보 */
	private PaginationInfo paginationInfo;

}
