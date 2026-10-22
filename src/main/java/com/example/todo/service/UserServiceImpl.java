package com.example.todo.service;

import com.example.todo.domain.User;
import com.example.todo.repository.UserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * UserService の実装。
 *
 * ユーザー登録や検索などの
 * ユーザー関連のビジネスロジックを提供する。
 *
 * 永続化処理は UserRepository に委譲し、
 * Resource 層からは UserService インターフェース経由で利用される。
 */
@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    /**
     * 新しいユーザーを作成して保存する。
     *
     * @param username ユーザー名
     * @param passwordHash ハッシュ化済みパスワード
     * @return 作成したユーザー
     */
    @Override
    @Transactional
    public User create(String username, String password) {

        User user = new User();

        String passwordHash =
                BCrypt.withDefaults()
                        .hashToString(
                                12,
                                password.toCharArray());

        user.setUsername(username);
        user.setPasswordHash(passwordHash);

        userRepository.persist(user);

        return user;
    }

    /**
     * ユーザー名で検索する。
     *
     * @param username ユーザー名
     * @return 存在する場合は User、存在しない場合は空
     */
    @Override
    public Optional<User> findByUsername(String username) {

        return userRepository
                .find("username", username)
                .firstResultOptional();
    }
}