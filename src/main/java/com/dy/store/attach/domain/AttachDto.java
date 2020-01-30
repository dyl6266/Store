package com.dy.store.attach.domain;

import com.dy.store.common.domain.CommonDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class AttachDto extends CommonDto {

	/** PK */
	private Long id;

	/** 상품 코드 */
	private String code;

	/** 원본 파일명 */
	private String originalName;

	/** 저장 파일명 */
	private String storedName;

	/** 파일 크기 */
	private long size;

	@Builder
	public AttachDto(Long id, String code, String originalName, String storedName, long size) {
		this.id = id;
		this.code = code;
		this.originalName = originalName;
		this.storedName = storedName;
		this.size = size;
	}

}
