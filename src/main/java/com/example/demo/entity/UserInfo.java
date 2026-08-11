package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

/**
 * DB内のユーザ情報を取得するためEntityクラスです。
 */
@Entity
@Table(name = "user_info") // table名を記載する
@Data
public class UserInfo {

	/** ログインID */
	@Id
	@Column(name = "login_id") // 変数名とカラム名を一致させる
	private String loginId;

	/** パスワード */
	private String password;

	/** 権限名 */
	@Column(name = "role_name")
	private String roleName;

}
