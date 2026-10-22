package com.example.todo.service;

import com.example.todo.domain.User;

import java.util.Optional;

/**
 * ユーザーのビジネスロジックの契約（インターフェース）。
 *
 * Resource（HTTP 入出力）はこのインターフェースに依存する。
 * 実装は UserServiceImpl。
 *
 * ユーザー登録、ログイン認証などの
 * ユーザー関連業務を担当する。
 */
public interface UserService {

    /** ユーザー登録。 */
    boolean create(String username, String password);

    /** ユーザー名で検索。 */
    Optional<User> findByUsername(String username);
}