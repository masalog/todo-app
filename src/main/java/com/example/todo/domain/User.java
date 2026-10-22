package com.example.todo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * ユーザーエンティティ。
 *
 * ログインユーザー情報を保持する。
 * username はログインIDとして利用し、重複を許可しない。
 * passwordHash には平文パスワードではなく、
 * BCrypt等でハッシュ化した値を保存する。
 *
 * Lombok の @Data が getter/setter/toString/equals/hashCode を、
 * @NoArgsConstructor が引数なしコンストラクタを自動生成する。
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    /** ユーザーID（主キー） */
    @Id
    @GeneratedValue
    private Long id;

    /** ログインユーザー名（重複不可） */
    @Column(nullable = false, unique = true)
    private String username;

    /** ハッシュ化されたパスワード */
    @Column(nullable = false)
    private String passwordHash;
}