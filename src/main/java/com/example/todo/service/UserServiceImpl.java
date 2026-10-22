package com.example.todo.service;

import com.example.todo.domain.User;
import com.example.todo.repository.UserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
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
     * @param password 平文パスワード
     * @return 登録成功時は true
     */
    @Override
    @Transactional
    public boolean create(String username, String password) {

        try {

            User user = new User();

            String passwordHash =
                    BCrypt.withDefaults()
                            .hashToString(
                                    12,
                                    password.toCharArray());

            user.setUsername(username);
            user.setPasswordHash(passwordHash);

            /*
             * flush まで実行することで、
             * UNIQUE 制約違反をこのメソッド内で検出する。
             */
            userRepository.persistAndFlush(user);

            return true;

        } catch (PersistenceException e) {

            /*
             * username の UNIQUE 制約違反は
             * 登録失敗として扱う。
             */
            if (isUniqueConstraintViolation(e)) {
                return false;
            }

            /*
             * それ以外の永続化エラーは握りつぶさない。
             */
            throw e;
        }
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

    /**
     * PostgreSQL の UNIQUE 制約違反か判定する。
     *
     * SQLState 23505 は unique_violation を表す。
     *
     * @param error 発生した例外
     * @return UNIQUE 制約違反の場合 true
     */
    private boolean isUniqueConstraintViolation(Throwable error) {

        for (Throwable cause = error;
             cause != null;
             cause = cause.getCause()) {

            if (cause instanceof SQLException sqlException
                    && "23505".equals(sqlException.getSQLState())) {
                return true;
            }
        }

        return false;
    }
}