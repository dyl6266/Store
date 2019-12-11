package com.dy.store.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dy.store.security.CustomAccessDeniedHandler;
import com.dy.store.security.CustomAuthenticationProvider;
import com.dy.store.security.LoginFailureHandler;
import com.dy.store.security.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationProvider authProvider;

	/**
	 * 스프링에서 권장하는 hash 알고리즘
	 * 
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 로그인 성공 핸들러
	 * 
	 * @return LoginSuccessHandler
	 */
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new LoginSuccessHandler();
	}

	/**
	 * 로그인 실패 핸들러
	 * 
	 * @return LoginFailureHandler
	 */
	@Bean
	public AuthenticationFailureHandler failureHandler() {
		return new LoginFailureHandler();
	}

	/**
	 * 접근 거부 핸들러
	 * 
	 * @return CustomAccessDeniedHandler
	 */
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	/**
	 * 인증 프로바이더 등록
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	/**
	 * static 디렉터리에 존재하는 정적 리소스 파일 제외
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/user/css/**", "/user/font/**", "/user/images/**", "/user/jquery/**",
				"/user/js/**");
	}

	/**
	 * HTTP 통신 관련 설정
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/* 페이지 권한 설정 */
		http.authorizeRequests()
		.antMatchers("/admin/**").hasAnyRole("ADMIN", "MANAGER") // 관리자 페이지
		.antMatchers("/**")
		.permitAll();

		/* 로그인 설정 */
		http.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/auth")
		.successHandler(successHandler())
		.failureHandler(failureHandler())
		.usernameParameter("email")
		.passwordParameter("password")
		.permitAll();

		/* 로그아웃 설정 */
		http.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout")
		.invalidateHttpSession(true)
		.permitAll();

		/* 403 예외 핸들러 */
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
	}

}
