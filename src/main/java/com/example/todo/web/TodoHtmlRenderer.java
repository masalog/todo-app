package com.example.todo.web;

import com.example.todo.domain.Todo;

import java.util.List;

/**
 * Todo を htmx 用の HTML 断片に描画する View 層の契約（インターフェース）。
 *
 * Resource はこのインターフェースに依存する。実装は {@link TodoHtmlRendererImpl}。
 * 描画方式を差し替えられるよう（将来テンプレートエンジン化する等）、
 * また他の層（Repository / Service）と一貫させるためインターフェースとして定義する。
 */
public interface TodoHtmlRenderer {

    /** 一覧（<li> の連なり）を描画する。空なら空メッセージを返す。 */
    String renderList(List<Todo> todos);

    /** Todo 1 件を <li> 断片として描画する。 */
    String renderTodo(Todo todo);
}
