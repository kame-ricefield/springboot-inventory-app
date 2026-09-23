# Spring Boot 在庫管理アプリ

## 📌 概要

本アプリは、商品在庫を管理するためのWebアプリケーションです。
Spring Bootを用いて、ログイン認証付きの在庫管理機能を実装しています。
ユーザー登録、ログイン、在庫検索、在庫更新、CSV出力の一連の流れを含む構成になっており、実務を意識したMVCベースのアプリとして作成しています。

---

## 🎯 作成目的

* Spring Boot を使った Web アプリケーション開発の理解を深める
* MVC 構成と認証フローを実装し、実務的な設計に触れる
* JPA / PostgreSQL / Thymeleaf を組み合わせたアプリを構築する

---

## 🛠 使用技術

* Java 21
* Spring Boot 4.0.0
* Spring MVC
* Spring Security
* Spring Data JPA
* Thymeleaf
* PostgreSQL
* HTML / CSS / Bootstrap
* Lombok
* Validation

---

## 💡 主な機能

* 🔐 ユーザー登録機能
* 🔑 ログイン機能（Spring Security）
* 📋 在庫一覧表示
* 🔎 商品名での部分一致検索
* ➕ 商品の新規登録と在庫数の更新
* 📦 在庫数が0未満になる更新の防止
* 📄 現在の在庫一覧を CSV 形式でダウンロード
* 👤 権限に応じた在庫更新制御（一般ユーザーは更新不可）

---

## 🖥 画面イメージ

### 在庫管理画面

![在庫管理画面](./img/inventory.png)

---

## 📚 実装上のポイント

* Spring Security によるログイン認証の導入
* Controller / Service / Repository / Entity の分離
* 商品名検索と在庫数更新を同一画面で処理
* CSV 出力機能の実装
* バリデーションと重複ユーザー登録の防止
* PostgreSQL を利用したデータ永続化

---

## 👤 作成者

kame-ricefield

