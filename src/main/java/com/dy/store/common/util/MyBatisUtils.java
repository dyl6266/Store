package com.dy.store.common.util;

import org.apache.logging.log4j.util.Strings;
import org.springframework.util.ObjectUtils;

public class MyBatisUtils {

	/**
	 * 오브젝트가 타입에 관계없이 비어있는지 확인
	 * 
	 * @param objs - 일정하지 않은 개수의 오브젝트
	 * 
	 * @return boolean
	 */
	public static boolean isEmpty(Object... objs) {

		if (ObjectUtils.isEmpty(objs) == true) {
			return true;
		}

		for (Object obj : objs) {
			if (Strings.isBlank(String.valueOf(obj)) == true || ObjectUtils.isEmpty(obj) == true) {
				return true;
			}
			continue;
		}

		return false;
	}

}
