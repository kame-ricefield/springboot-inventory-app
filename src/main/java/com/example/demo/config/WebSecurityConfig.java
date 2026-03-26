package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.constant.UrlConst;

/*
 * Spring securityの認証に関する定義を行います。
 */
@EnableWebSecurity//Spring Securityを有効化する
@Configuration//Springの設定クラスである事を表す
public class WebSecurityConfig {
	
	/* ユーザ名のname属性*/
	private final String USERNAME_PARAMETER = "loginId";
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		//特定のURLへの遷移を制限する
		http.authorizeHttpRequests(authorize -> authorize.requestMatchers(UrlConst.NO_AUTHENTICATION).permitAll().anyRequest().authenticated())
		
		//usernameParameter:デフォルトのusername属性を任意値に変更する
		//defaultSuccessUrl:ログイン先のURLをデフォルトのindex.htmlから任意値に変更する
			.formLogin(login -> login.loginPage(UrlConst.LOGIN).usernameParameter(USERNAME_PARAMETER).defaultSuccessUrl(UrlConst.INVENTORY, true));
		
		return http.build();
	}
	
}
