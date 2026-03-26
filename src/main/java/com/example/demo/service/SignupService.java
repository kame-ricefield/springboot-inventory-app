package com.example.demo.service;

import java.util.Optional;

import org.dozer.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignupForm;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

/**
 * ユーザ登録画面から渡された引数を元にDBからユーザ情報を取得するServiceクラスです。
 */
@Service
@RequiredArgsConstructor
public class SignupService {
	
	/** ユーザ情報テーブルDAO */
	private final UserInfoRepository repository;
	
	/** Dozer Mapper */
	private final Mapper mapper;
	
	/** パスワードエンコーダー */
	private final PasswordEncoder passwordEncoder;
	
	/**
	 * ユーザ情報テーブル 新規登録
	 * @param form 入力情報
	 * @return ユーザ情報Entity※既に同じユーザIDで登録がある場合はempty(空)を返す
	 */
	public Optional<UserInfo> resistUserInfo(SignupForm form){
		var userInfoExistOpt = repository.findById(form.getLoginId());
		if(userInfoExistOpt.isPresent()) {
			return Optional.empty();
		}
				
		var userInfo = mapper.map(form, UserInfo.class);
		var encodedPassword = passwordEncoder.encode(form.getPassword());
		userInfo.setPassword(encodedPassword);
		
		return Optional.of(repository.save(userInfo));
	}

}
