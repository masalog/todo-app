package com.example.todo.service;

import com.example.todo.domain.Todo;

import java.util.List;
import java.util.Optional;

/**
 * Todo のビジネスロジックの契約（インターフェース）。
 *
 * Resource（HTTP 入出力）はこのインターフェースに依存する。
 * 実装は {@link TodoServiceImpl}。テストでモックに差し替えやすくするため、
 * また実装の切り替えに備えてインターフェースとして定義する。
 */
public interface TodoService {

    /** 全件取得。 */
    List<Todo> findAll();

    /** 1 件取得。 */
    Optional<Todo> findById(Long id);

    /** 新しい Todo を作成して保存し、作成した Todo を返す。 */
    Todo create(String title);

    /** 完了状態を反転。対象が存在すれば更新後の Todo を、無ければ空を返す。 */
    Optional<Todo> toggle(Long id);

    /** 削除。存在した場合に true。 */
    boolean delete(Long id);
}
