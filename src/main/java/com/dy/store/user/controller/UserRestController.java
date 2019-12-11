package com.dy.store.user.controller;

import javax.validation.Valid;

import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dy.store.authentication.domain.AuthenticationDto;
import com.dy.store.authentication.service.AuthenticationService;
import com.dy.store.user.domain.UserDto;
import com.dy.store.user.service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class UserRestController {

	private UserService userService;

	private AuthenticationService authService;

	/**
	 * 회원가입 인증메일 발송
	 * 
	 * @param params - AuthenticationDto
	 * @return 메시지, 결과를 담은 Json
	 */
	@PostMapping(value = "/users/authentication")
	public JsonObject sendAuthenticationMail(@RequestBody final AuthenticationDto params) {

		JsonObject jsonObj = new JsonObject();

		/* 이메일 패턴 정규식 체크 */
		String regexp = "^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{1,5}$";
		if (params.getEmail().matches(regexp) == false) {
			jsonObj.addProperty("message", "아이디를 이메일 형식으로 입력해 주세요.");
			jsonObj.addProperty("result", false);
			return jsonObj;
		}

		/* 아이디 중복 체크 */
		UserDto user = (UserDto) userService.loadUserByUsername(params.getEmail());
		if (user != null) {
			jsonObj.addProperty("message", "이미 사용 중이거나 탈퇴된 아이디입니다.");
			jsonObj.addProperty("result", false);
			return jsonObj;
		}

		/* 메일 발송 */
		try {
			boolean wasMailSent = authService.sendAuthenticationMail(params.getEmail());
			if (wasMailSent == false) {
				jsonObj.addProperty("message", "인증 메일 발송에 실패하였습니다.");
			}
			jsonObj.addProperty("result", wasMailSent);

		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 중에 문제가 발생하였습니다.");
			jsonObj.addProperty("result", false);

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
			jsonObj.addProperty("result", false);
		}

		return jsonObj;
	}

	/**
	 * 메일로 발송된 인증번호 확인
	 * 
	 * @param email - 아이디
	 * @param number - 인증번호
	 * @return 메시지, 결과를 담은 Json
	 */
	@PatchMapping(value = "/users/authentication/{email}/{number}")
	public JsonObject checkAuthenticationNumber(@PathVariable final String email, @PathVariable final String number) {

		JsonObject jsonObj = new JsonObject();

		/* 인증 정보 조회 */
		AuthenticationDto auth = authService.getAuthenticationDetail(email, number);
		if (auth == null) {
			jsonObj.addProperty("message", "유효하지 않은 인증번호입니다.");
			jsonObj.addProperty("result", false);
			return jsonObj;
		}

		/* 인증 상태 변경 */
		try {
			boolean isModified = authService.modifyAuthenticationStatus(email, number);
			if (isModified == false) {
				jsonObj.addProperty("message", "인증 상태 변경에 실패하였습니다.");
			}
			jsonObj.addProperty("result", isModified);

		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 중에 문제가 발생하였습니다.");
			jsonObj.addProperty("result", false);

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
			jsonObj.addProperty("result", false);
		}

		return jsonObj;
	}

	/**
	 * 사용자 회원가입
	 * 
	 * @param params - UserDto
	 * @param bindingResult - 빈 유효성 검증 오브젝트
	 * @return 메시지, 결과를 담은 Json
	 */
	@PostMapping(value = "/users")
	public JsonObject registerUser(@Valid @RequestBody final UserDto params, BindingResult bindingResult) {

		JsonObject jsonObj = new JsonObject();

		/* 유효성 검증에 실패한 경우 */
		if (bindingResult.hasErrors() == true) {
			/* 에러 개수가 1개를 초과하는 경우 */
			if (bindingResult.getErrorCount() > 1) {
				jsonObj.addProperty("message", "회원가입에 필요한 정보를 모두 올바른 형식으로 입력해 주세요.");
			} else {
				String errorMessage = bindingResult.getFieldError().getDefaultMessage();
				jsonObj.addProperty("message", errorMessage);
			}
			jsonObj.addProperty("result", false);
			return jsonObj;
		}

		try {
			boolean isRegistered = userService.registerUser(params);
			if (isRegistered == false) {
				jsonObj.addProperty("message", "회원가입에 실패하였습니다.");
			}
			jsonObj.addProperty("result", isRegistered);

		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 중에 문제가 발생하였습니다.");
			jsonObj.addProperty("result", false);

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
			jsonObj.addProperty("result", false);
		}

		return jsonObj;
	}

	/**
	 * 회원정보 상세 조회
	 * 
	 * @param params - UserDto
	 * @return 메시지, 결과를 담은 Json
	 */
	@GetMapping(value = "/users")
	public JsonObject getUserDetail(final UserDto params) {

		JsonObject jsonObj = new JsonObject();

		UserDto user = (UserDto) userService.getUserDetail(params);
		if (user != null) {
			JsonElement jsonElem = new Gson().toJsonTree(user);
			jsonObj.add("user", jsonElem);
		}

		return jsonObj;
	}

}
