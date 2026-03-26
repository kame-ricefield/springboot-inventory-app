package com.example.demo.constant;

/*
 * URLを定数としてまとめて管理するためのクラスです。
 */
public class UrlConst {
	/* ログイン画面 */
	public static final String LOGIN = "/login";
	
	/* ユーザ登録画面 */
	public static final String SIGNUP = "/signup";
	
	/* 在庫管理画面 */
	public static final String INVENTORY = "/inventory";
	
	/* 在庫更新処理 */
	public static final String INVENTORYUPDATE = "/inventory/update";
	
	/* 認証不要画面 */
	public static final String[] NO_AUTHENTICATION = {LOGIN, SIGNUP, "/webjars/**"};
	
}
