package com.dy.store.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

	/**
	 * 파일 업로드로 들어오는 데이터를 처리하는 빈(Bean)
	 * @return
	 */
	@Bean
	public CommonsMultipartResolver multipartResolver() {

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("UTF-8"); /* 인코딩 */
		multipartResolver.setMaxUploadSize(5 * 1024 * 1024); /* 최대 업로드 크기 */
		multipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024); /* 파일당 최대 업로드 크기 */
		multipartResolver.setMaxInMemorySize(0); /* 디스크에 저장하지 않고, 메모리에 유지되도록 허용하는 값 (설정한 크기 이상의 데이터는 파일에 저장) */
		return multipartResolver;
	}

}
