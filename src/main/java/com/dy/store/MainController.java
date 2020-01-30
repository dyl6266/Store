package com.dy.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class MainController {

	@GetMapping(value = "/admin/test")
	@ResponseBody
	public String authorityTest() {
		return "Hello World!";
	}
	
	@GetMapping(value = "/access-denied")
	@ResponseBody
	public String accessDeniedTest() {
		return "접근이 허용되지 않은 계정입니다!";
	}

}
