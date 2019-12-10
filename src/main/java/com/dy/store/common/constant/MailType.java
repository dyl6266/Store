package com.dy.store.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MailType {

	/** 전송할 메일의 타입 */
	TEXT("text"), HTML("html"), FILE("file");

	private String value;
}
