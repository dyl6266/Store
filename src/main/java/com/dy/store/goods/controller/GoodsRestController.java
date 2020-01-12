package com.dy.store.goods.controller;

import javax.validation.Valid;

import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dy.store.goods.domain.GoodsDto;
import com.dy.store.goods.service.GoodsService;
import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class GoodsRestController {

	private GoodsService goodsService;

	/**
	 * 관리자 상품 등록
	 * 
	 * @param params - GoodsDto
	 * @param bindingResult - 빈 유효성 검증 오브젝트
	 * @return 메시지, 결과를 담은 Json
	 */
	@PostMapping(value = "/goods")
	public JsonObject registerGoods(@Valid GoodsDto params, BindingResult bindingResult) {

		JsonObject jsonObj = new JsonObject();

		/* 유효성 검증에 실패한 경우 */
		if (bindingResult.hasErrors() == true) {
			/* 에러 개수가 1개를 초과하는 경우 */
			if (bindingResult.getErrorCount() > 1) {
				jsonObj.addProperty("message", "상품 등록에 필요한 정보를 모두 입력해 주세요.");
			} else {
				String errorMessage = bindingResult.getFieldError().getDefaultMessage();
				jsonObj.addProperty("message", errorMessage);
			}
			jsonObj.addProperty("result", false);
			return jsonObj;
		}

		try {
			boolean isRegistered = goodsService.registerGoods(params);
			if (isRegistered == false) {
				jsonObj.addProperty("message", "상품 등록에 실패하였습니다.");
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
	 * 상품 정보 수정
	 * 
	 * @param code - 상품 코드
	 * @param params - GoodsDto
	 * @param bindingResult - 빈 유효성 검증 오브젝트
	 * @return 메시지, 결과를 담은 Json
	 */
	@PatchMapping(value = "/goods/{code}")
	public JsonObject modifyGoodsInfo(@PathVariable String code, @Valid GoodsDto params, BindingResult bindingResult) {

		JsonObject jsonObj = new JsonObject();

		/* 유효성 검증에 실패한 경우 */
		if (bindingResult.hasErrors() == true) {
			/* 에러 개수가 1개를 초과하는 경우 */
			if (bindingResult.getErrorCount() > 1) {
				jsonObj.addProperty("message", "상품 등록에 필요한 정보를 모두 입력해 주세요.");
			} else {
				String errorMessage = bindingResult.getFieldError().getDefaultMessage();
				jsonObj.addProperty("message", errorMessage);
			}
			jsonObj.addProperty("result", false);
			return jsonObj;
		}

		try {
			boolean isModified = goodsService.registerGoods(params);
			if (isModified == false) {
				jsonObj.addProperty("message", "상품 정보 수정에 실패하였습니다.");
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

}
