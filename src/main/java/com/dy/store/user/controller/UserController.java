package com.dy.store.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dy.store.user.domain.UserDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class UserController {

	private HttpServletRequest request;

	/**
	 * 회원가입 화면
	 * 
	 * @param model
	 * @return HTLM
	 */
	@GetMapping(value = "/signup")
	public String openSignUp(Model model) {

		/* Thymeleaf의 th:object를 사용하기 위해 비어있는 UserDto 인스턴스를 뷰로 전달 */
		model.addAttribute("user", UserDto.builder().build());

		return "signup";
	}

	/**
	 * 로그인 화면 (매핑을 Get으로 처리하면 LoginFailureHandler에서 Forward 처리에 문제가 생김)
	 * 
	 * @param model
	 * @return HTML
	 */
	@RequestMapping(value = "/login")
	public String openLogin(Model model) {

		/* 이전 페이지 URI */
		String referer = request.getHeader("referer");
		if (Strings.isBlank(referer) == false) {
			request.getSession().setAttribute("referer", referer);
		}

		return "login";
	}

}
