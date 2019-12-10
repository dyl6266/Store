package com.dy.store.common.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.dy.store.common.constant.MailType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class MailUtils {

	private JavaMailSender mailSender;

	/**
	 * 메일 발송
	 * 
	 * @param email - 메일을 발송할 아이디
	 * @param subject - 제목
	 * @param text - 내용
	 * @param mailType - 메일 유형 (TEXT, HTML, FILE)
	 * 
	 * @return boolean
	 */
	public boolean sendMailByEmail(String email, String subject, String text, MailType mailType) {

		/* 텍스트 타입 메시지의 경우 */
		if (mailType == MailType.TEXT) {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email);
			message.setSubject(subject);
			message.setText(text);

			mailSender.send(message);

		/* HTML 또는 첨부 파일 메시지의 경우 */
		} else {
			MimeMessage message = mailSender.createMimeMessage();
			try {
				MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
				helper.setTo(email);
				helper.setSubject(subject);
				helper.setText(text, true);

				mailSender.send(message);

			} catch (MessagingException e) {
				return false;

			} catch (Exception e) {
				return false;
			}
		}

		return true;
	}

}
