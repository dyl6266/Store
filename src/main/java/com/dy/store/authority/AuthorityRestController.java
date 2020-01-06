package com.dy.store.authority;

import javax.validation.Valid;

import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dy.store.authority.constant.Authority;
import com.dy.store.authority.domain.AuthorityDto;
import com.dy.store.authority.service.AuthorityService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class AuthorityRestController {

	private AuthorityService authorityService;

	/**
	 * 사용자 권한 추가
	 * 
	 * @param params - AuthorityDto
	 * @param bindingResult - 빈 유효성 검증 오브젝트
	 * @return 메시지, 결과를 담은 Json
	 */
	@PostMapping(value = "/authorities")
	public JsonObject registerUserAuthority(@Valid @RequestBody final AuthorityDto params, BindingResult bindingResult) {

		JsonObject jsonObj = new JsonObject();

		/* 유효성 검증에 실패한 경우 */
		if (bindingResult.hasErrors() == true) {
			/* 에러 개수가 1개를 초과하는 경우 */
			if (bindingResult.getErrorCount() > 1) {
				jsonObj.addProperty("message", "권한 추가에 필요한 정보를 모두 입력해 주세요.");
			} else {
				String errorMessage = bindingResult.getFieldError().getDefaultMessage();
				jsonObj.addProperty("message", errorMessage);
			}
			jsonObj.addProperty("result", false);
			return jsonObj;
		}

		/* 사용자 권한 등록 */
		try {
			boolean isRegistered = authorityService.registerUserAuthority(params);
			if (isRegistered == false) {
				jsonObj.addProperty("message", "권한 추가에 실패하였습니다.");
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
	 * 사용자 권한 조회
	 * 
	 * @param email - 아이디
	 * @param name - 권한명
	 * @return 사용자 권한 정보를 담은 Json
	 */
	@GetMapping(value = "/authorities/{email}/{name}")
	public JsonObject getUserAuthorityDetail(@PathVariable final String email, @PathVariable final Authority name) {

		JsonObject jsonObj = new JsonObject();

		AuthorityDto authority = authorityService.getUserAuthorityDetail(email, name);
		if (authority != null) {
			JsonElement jsonElem = new Gson().toJsonTree(authority);
			jsonObj.add("authority", jsonElem);
		}

		return jsonObj;
	}

	/**
	 * 사용자 권한 상태 변경
	 * 
	 * @param email - 아이디
	 * @param params - AuthorityDto
	 * @return 메시지, 결과를 담은 Json
	 */
	@PatchMapping(value = "/authorities/{email}")
	public JsonObject changeUserAuthorityStatus(@PathVariable final String email,
												@RequestBody final AuthorityDto params) {

		JsonObject jsonObj = new JsonObject();

		try {
			boolean isChanged = authorityService.changeUserAuthorityStatus(email, params);
			if (isChanged == false) {
				jsonObj.addProperty("message", "사용자 권한 상태 변경에 실패하였습니다.");
			}
			jsonObj.addProperty("result", isChanged);

		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 중에 문제가 발생하였습니다.");
			jsonObj.addProperty("result", false);

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
			jsonObj.addProperty("result", false);
		}

		return jsonObj;
	}

}
