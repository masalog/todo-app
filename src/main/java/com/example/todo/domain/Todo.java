package com.example.todo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Todo エンティティ。
 *
 * Lombok の @Data が getter/setter/toString/equals/hashCode を、
 * @NoArgsConstructor が引数なしコンストラクタを自動生成する。
 * boolean フィールドの completed には isCompleted()/setCompleted() が生成される。
 */
@Entity
@Table(name = "todo")
@Data
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private boolean completed;
    private Instant createdAt;
}
