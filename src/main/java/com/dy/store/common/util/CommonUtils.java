package com.dy.store.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

import com.dy.store.common.constant.YesNo;

public class CommonUtils {

	/**
	 * 숫자를 포함한 랜덤 문자열 32글자를 반환
	 * 
	 * @return 랜덤 문자열
	 */
	public static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 숫자를 포함한 랜덤 문자열 10글자를 반환
	 * 
	 * @param length - 생성할 숫자 길이
	 * 
	 * @return 랜덤 문자열
	 */
	public static String getRandomString(int length) {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
	}

	/**
	 * length 만큼의 랜덤 숫자 반환
	 * 
	 * @param length - 생성할 숫자 길이
	 * 
	 * @return 랜덤 숫자
	 */
	public static String getRandomNumber(int length) {

		String result = "";
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			/* 0 ~ 9 사이의 난수 */
			String randomNumber = Integer.toString(random.nextInt(10));
			result += randomNumber;
		}

		return result;
	}

	/**
	 * length 만큼의 랜덤 숫자 반환
	 * 
	 * @param length - 생성할 숫자 길이
	 * @param useYn  - 중복 허용 여부
	 * 
	 * @return 랜덤 숫자
	 */
	public static String getRandomNumber(int length, YesNo yesNo) {

		String result = "";
		Random random = new Random();

		if (yesNo == YesNo.Y) {
			for (int i = 0; i < length; i++) {
				/* 0 ~ 9 사이의 난수 */
				String randomNumber = Integer.toString(random.nextInt(10));
				result += randomNumber;
			}

		} else {
			for (int i = 0; i < length; i++) {
				/* 0 ~ 9 사이의 난수 */
				String randomNumber = Integer.toString(random.nextInt(10));

				/* result에 randomNumber와 동일한 숫자가 존재하면 continue */
				if (result.contains(randomNumber)) {
					i -= 1;
					continue;
				}
				result += randomNumber;
			}
		}

		return result;
	}

	/**
	 * 현재 날짜, 시간을 문자열 형태로 반환
	 * 
	 * @return 현재 날짜, 시간
	 */
	public static String getCurrentTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 패턴에 일치하는 형태의 날짜를 문자열 형태로 반환
	 * 
	 * @param dateTime - 시간
	 * @param pattern - 패턴
	 * 
	 * @return 문자열 형태의 날짜
	 */
	public static String formatDate(LocalDateTime dateTime, String pattern) {
		return dateTime.format(DateTimeFormatter.ofPattern(pattern));
	}

}
