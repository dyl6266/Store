package com.dy.store.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dy.store.user.domain.UserDto;
import com.dy.store.user.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class UserController {

	private HttpServletRequest request;

	private UserService userService;

	/**
	 * 사용자 회원가입 화면
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
	 * 사용자 로그인 화면 (매핑을 Get으로 처리하면 LoginFailureHandler에서 Forward 처리에 문제가 생김)
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

	/**
	 * 관리자 사용자 목록 화면
	 * 
	 * @param params - UserDto
	 * @param isAjax - Ajax 호출 여부
	 * @param model
	 * @return HTML
	 */
	@GetMapping(value = "/admin/user/list")
	public String openAdminUserList(@ModelAttribute("params") UserDto params,
									@RequestParam(required = false) boolean isAjax,
									Model model) {

		List<UserDto> userList = userService.getUserList(params);
		model.addAttribute("users", userList);

		return (isAjax == false) ? "admin/user/list" : "admin/user/list-ajax";
	}

}
