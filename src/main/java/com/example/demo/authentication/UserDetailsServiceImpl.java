package com.example.demo.authentication;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

/**
 * ログイン時にrepositoryから取得したentity情報をSpring Securityに渡すユーザ情報取得クラスです。
 */
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    /** ユーザー情報テーブルRepository */
    private final UserInfoRepository repository;

    /**
     * ユーザー情報を生成します。
     *
     * @param username ログインID
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userInfo = repository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));//ユーザ情報未取得の時、例外クラス起動

        return User.withUsername(userInfo.getLoginId())
                .password(userInfo.getPassword())
                .roles("USER")//権限
                .build();//以上の情報でUserを作ります
    }

}
