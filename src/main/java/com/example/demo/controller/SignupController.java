package com.example.demo.controller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.UrlConst;
import com.example.demo.form.SignupForm;
import com.example.demo.service.SignupService;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;

/**
 * ユーザ登録画面のコントローラーです。
 */

@Controller
@RequiredArgsConstructor
public class SignupController {

	/** ログイン画面 service */
	private final SignupService service;

	/** メッセージソース */
	private final MessageSource messageSource;

	/**
	 * 画面の初期表示を行います。
	 * 
	 * @param model モデル
	 * @param form 入力情報
	 * @return ユーザー登録画面
	 */
	@GetMapping(UrlConst.SIGNUP)
	public String view(Model model, SignupForm form) {
		return "signup";
	}

	/**
	 * 画面の入力情報からユーザー登録処理を呼び出します。
	 * 
	 * <p>ただし、入力チェックでエラーになった場合や登録済みのログインIDを使っていた場合は<br>
	 * エラーメッセージを画面に表示します。
	 * 
	 * @param model モデル
	 * @param form 入力情報
	 * @param bdResult 入力内容の単項目チェック結果
	 */
	@PostMapping(UrlConst.SIGNUP)
	public void signup(Model model, @Validated SignupForm form, BindingResult beResult) {
		if (beResult.hasErrors()) {
			var errorMsg = AppUtil.getMessage(messageSource, "common.formError");
			model.addAttribute("message", errorMsg);
			return;
		}

		var userInfoOpt = service.resistUserInfo(form);
		if (userInfoOpt.isEmpty()) {
			var errorMsg = AppUtil.getMessage(messageSource, "signup.existedLoginId");
			model.addAttribute("message", errorMsg);
		} else {
			var errorMsg = AppUtil.getMessage(messageSource, "signup.resistSucceed");
			model.addAttribute("message", errorMsg);
		}
	}

}
