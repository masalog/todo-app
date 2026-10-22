package com.example.todo.repository;

import com.example.todo.domain.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * User 永続化リポジトリ。
 *
 * PanacheRepository が CRUD 機能を提供するため、
 * 基本的には実装コードを持たない。
 *
 * 複雑な業務ロジックは Service 層で扱う。
 * このクラスは永続化のみ担当する。
 */
@ApplicationScoped
public class UserRepository
        implements PanacheRepository<User> {
}