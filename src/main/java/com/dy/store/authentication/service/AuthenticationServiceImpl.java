package com.dy.store.authentication.service;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.dy.store.authentication.domain.AuthenticationDto;
import com.dy.store.authentication.mapper.AuthenticationMapper;
import com.dy.store.common.constant.MailType;
import com.dy.store.common.util.CommonUtils;
import com.dy.store.common.util.MailUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private AuthenticationMapper authMapper;

	private MailUtils mailUtils;

	/**
	 * 인증 메일 발송
	 * 
	 * @param email - 아이디
	 * 
	 * @return boolean
	 */
	public boolean sendAuthenticationMail(String email) {

		try {
			/* 인증번호 생성 & Insert */
			String number = CommonUtils.getRandomNumber(8);
			AuthenticationDto params = AuthenticationDto.builder().email(email).number(number).build();

			int queryResult = authMapper.insertAuthenticationInfo(params);
			if (queryResult != 1) {
				return false;
			}

			/* HTML 파일 경로 */
			final String pathname = "C:" + File.separator + "html" + File.separator + "mail.html";
			/* File 객체를 HTML로 파싱 */
			Document doc = Jsoup.parse(new File(pathname), "UTF-8");
			if (doc.getElementById("email") == null || doc.getElementById("number") == null) {
				return false;
			}

			/* 사용자에게 이메일, 인증번호를 보여주는 엘러먼트에 텍스트 설정 */
			doc.getElementById("email").text(email);
			doc.getElementById("number").text(number);

			/* 메일 발송 */
			String html = String.valueOf(doc);
			boolean wasMailSent = mailUtils.sendMailByEmail(email, "DY 스토어 가입 인증번호", html, MailType.HTML);
			if (wasMailSent == false) {
				return false;
			}

		} catch (IOException e) {
			return false;

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * 인증 정보 조회
	 * 
	 * @param email - 아이디
	 * @param number - 인증번호
	 * 
	 * @return AuthenticationDto
	 */
	@Override
	public AuthenticationDto getAuthenticationDetail(String email, String number) {

		AuthenticationDto params = AuthenticationDto.builder().email(email).number(number).build();
		return authMapper.selectAuthenticationDetail(params);
	}

	/**
	 * 인증 상태 변경
	 * 
	 * @param email - 아이디
	 * @param number - 인증번호
	 * 
	 * @return boolean
	 */
	@Override
	public boolean modifyAuthenticationStatus(String email, String number) {

		AuthenticationDto params = AuthenticationDto.builder().email(email).number(number).build();
		int queryResult = authMapper.updateAuthenticationStatus(params);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

}
