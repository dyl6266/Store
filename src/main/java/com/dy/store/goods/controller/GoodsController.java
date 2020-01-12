package com.dy.store.goods.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.dy.store.goods.domain.GoodsDto;
import com.dy.store.goods.service.GoodsService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class GoodsController {

	private HttpServletRequest request;

	private GoodsService goodsService;

	/**
	 * 관리자 페이지 상품 등록 화면
	 * 
	 * @param code - 상품 코드 (신규 등록 & 수정 구분에 사용되는 파라미터)
	 * @param model
	 * @return HTML
	 */
	@GetMapping(value = "/admin/goods/register")
	public String openGoodsRegister(@RequestParam(required = false) String code, Model model) {

		if (Strings.isBlank(code) == true) {
			/* Thymeleaf의 th:object를 사용하기 위해 비어있는 GoodsDto 인스턴스를 뷰로 전달 */
			model.addAttribute("goods", GoodsDto.builder().build());

		} else {
			GoodsDto goods = goodsService.getGoodsDetail(code);
			if (goods.getStock() != null) {
				model.addAttribute("goods", goods);

				/* 상품 옵션(사이즈, 수량) 문자열을 JsonObject에 key : value 형태로 저장 */
				JsonObject options = new Gson().fromJson(goods.getStock().getOptions(), JsonObject.class);
				model.addAttribute("options", options);
			}
		}

		return "admin/goods/register";
	}

	/**
	 * 관리자 페이지 상품 목록 화면
	 * 
	 * @param params - GoodsDto
	 * @param model
	 * @return HTML
	 */
	@GetMapping(value = "/admin/goods/list")
	public String openAdminGoodsList(@ModelAttribute("params") GoodsDto params,
			@RequestParam(required = false) boolean isAjax, Model model) {

		List<GoodsDto> goodsList = goodsService.getGoodsList(params);
		model.addAttribute("goods", goodsList);

		return (isAjax == false) ? "admin/goods/list" : "admin/goods/list-ajax";
	}

}
