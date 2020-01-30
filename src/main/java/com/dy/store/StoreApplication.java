package com.dy.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

/*
 * @SpringBootApplication => 스프링 부트에서 다양한 기능을 하는 핵심 애너테이션 (세 개의 애너테이션으로 구성)
 * 
 * @EnableAutoConfiguration => 스프링의 다양한 설정이 자동으로 완료됨
 * @ComponentScan => 자동으로 여러 가지 컴포넌트 클래스를 검색하고 검색된 컴포넌트 및 빈 클래스를 스프링 애플리케이션 컨텍스트에 등록
 * @Configuration => SpringBootConfiguration이라는 애너테이션에 포함된 것으로 해당 애너테이션이 붙은 클래스를 자바 기반의 설정 파일로 지정
 * 
 */
@SpringBootApplication(exclude = { MultipartAutoConfiguration.class }) /* 첨부 파일 관련 자동 구성 요소 사용 제외 */
public class StoreApplication {

	/*
	 * 기존의 스프링에서 사용하던 XML 없이 순수 Java로 애플리케이션을 실행할 수 있도록 해주는 메소드
	 */
	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}

}
